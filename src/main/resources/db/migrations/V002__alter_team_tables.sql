-- V002: destructive schema reset for gameplay tables.
--
-- Purpose:
-- - replace old trainer_teams design with teams + team_members
-- - add delete cascades for true child rows
-- - recreate core gameplay tables in one consistent shape
--
-- This migration intentionally discards existing gameplay data.
-- Use only when trainer/Pokemon/team/battle data can be wiped.
--
-- See Documentation/Team-Schema-Split-Guide.md for rationale.

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS battle_pending_actions;
DROP TABLE IF EXISTS battle_turn_history;
DROP TABLE IF EXISTS battles;
DROP TABLE IF EXISTS team_members;
DROP TABLE IF EXISTS teams;
DROP TABLE IF EXISTS trainer_teams;
DROP TABLE IF EXISTS pokemon_movesets;
DROP TABLE IF EXISTS pokemon_instances;
DROP TABLE IF EXISTS trainers;

SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE IF NOT EXISTS trainers (
    trainer_id       INT AUTO_INCREMENT PRIMARY KEY,
    discord_id       BIGINT UNIQUE NOT NULL,
    discord_username VARCHAR(50) NOT NULL,
    name             VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS pokemon_instances (
    instance_id      INT AUTO_INCREMENT PRIMARY KEY,
    trainer_id       INT NOT NULL,
    species          VARCHAR(50) NOT NULL,
    nickname         VARCHAR(50) NOT NULL,
    level            SMALLINT NOT NULL DEFAULT 5,
    nature           VARCHAR(20) NOT NULL,
    iv_hp            SMALLINT NOT NULL,
    iv_attack        SMALLINT NOT NULL,
    iv_defense       SMALLINT NOT NULL,
    iv_sp_attack     SMALLINT NOT NULL,
    iv_sp_defense    SMALLINT NOT NULL,
    iv_speed         SMALLINT NOT NULL,
    ev_hp            SMALLINT NOT NULL DEFAULT 0,
    ev_attack        SMALLINT NOT NULL DEFAULT 0,
    ev_defense       SMALLINT NOT NULL DEFAULT 0,
    ev_sp_attack     SMALLINT NOT NULL DEFAULT 0,
    ev_sp_defense    SMALLINT NOT NULL DEFAULT 0,
    ev_speed         SMALLINT NOT NULL DEFAULT 0,
    current_hp       INT NOT NULL,
    current_exp      INT NOT NULL DEFAULT 0,
    CONSTRAINT fk_pokemon_instances_trainer
        FOREIGN KEY (trainer_id) REFERENCES trainers(trainer_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS pokemon_movesets (
    instance_id      INT NOT NULL,
    slot_index       SMALLINT NOT NULL,
    move_name        VARCHAR(50) NOT NULL,
    current_pp       SMALLINT NOT NULL,
    PRIMARY KEY (instance_id, slot_index),
    CONSTRAINT chk_pokemon_movesets_slot
        CHECK (slot_index BETWEEN 0 AND 3),
    CONSTRAINT fk_pokemon_movesets_instance
        FOREIGN KEY (instance_id) REFERENCES pokemon_instances(instance_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS teams (
    team_id          INT AUTO_INCREMENT PRIMARY KEY,
    trainer_id       INT NOT NULL,
    team_name        VARCHAR(50) NOT NULL,
    UNIQUE KEY uq_teams_trainer_name (trainer_id, team_name),
    CONSTRAINT fk_teams_trainer
        FOREIGN KEY (trainer_id) REFERENCES trainers(trainer_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS team_members (
    team_id          INT NOT NULL,
    slot_index       SMALLINT NOT NULL,
    instance_id      INT NOT NULL,
    PRIMARY KEY (team_id, slot_index),
    UNIQUE KEY uq_team_members_instance (instance_id),
    CONSTRAINT chk_team_members_slot
        CHECK (slot_index BETWEEN 0 AND 5),
    CONSTRAINT fk_team_members_team
        FOREIGN KEY (team_id) REFERENCES teams(team_id) ON DELETE CASCADE,
    CONSTRAINT fk_team_members_instance
        FOREIGN KEY (instance_id) REFERENCES pokemon_instances(instance_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS battles (
    battle_id                    INT AUTO_INCREMENT PRIMARY KEY,
    trainer1_id                  INT NOT NULL,
    trainer2_id                  INT NOT NULL,
    trainer1_active_pokemon_id   INT NULL,
    trainer2_active_pokemon_id   INT NULL,
    trainer1_team_id             INT NULL,
    trainer2_team_id             INT NULL,
    winner_id                    INT NULL,
    status                       VARCHAR(20) NOT NULL,
    created_at                   TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at                   TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT chk_battles_distinct_trainers
        CHECK (trainer1_id <> trainer2_id),
    CONSTRAINT chk_battles_status
        CHECK (status IN ('PENDING', 'TEAM_SETUP', 'ACTIVE', 'FINISHED')),
    CONSTRAINT fk_battles_trainer1
        FOREIGN KEY (trainer1_id) REFERENCES trainers(trainer_id) ON DELETE CASCADE,
    CONSTRAINT fk_battles_trainer2
        FOREIGN KEY (trainer2_id) REFERENCES trainers(trainer_id) ON DELETE CASCADE,
    CONSTRAINT fk_battles_trainer1_active_pokemon
        FOREIGN KEY (trainer1_active_pokemon_id) REFERENCES pokemon_instances(instance_id) ON DELETE SET NULL,
    CONSTRAINT fk_battles_trainer2_active_pokemon
        FOREIGN KEY (trainer2_active_pokemon_id) REFERENCES pokemon_instances(instance_id) ON DELETE SET NULL,
    CONSTRAINT fk_battles_trainer1_team
        FOREIGN KEY (trainer1_team_id) REFERENCES teams(team_id) ON DELETE SET NULL,
    CONSTRAINT fk_battles_trainer2_team
        FOREIGN KEY (trainer2_team_id) REFERENCES teams(team_id) ON DELETE SET NULL,
    CONSTRAINT fk_battles_winner
        FOREIGN KEY (winner_id) REFERENCES trainers(trainer_id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS battle_pending_actions (
    battle_id                    INT NOT NULL,
    trainer_id                   INT NOT NULL,
    action_type                  VARCHAR(10) NOT NULL,
    move_slot_index              SMALLINT NULL,
    switch_pokemon_id            SMALLINT NULL,
    submitted_at                 TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (battle_id, trainer_id),
    CONSTRAINT chk_battle_pending_action_type
        CHECK (action_type IN ('MOVE', 'SWITCH')),
    CONSTRAINT chk_battle_pending_action_payload
        CHECK (
            (action_type = 'MOVE' AND move_slot_index BETWEEN 0 AND 3 AND switch_pokemon_id IS NULL)
            OR
            (action_type = 'SWITCH' AND switch_pokemon_id BETWEEN 0 AND 5 AND move_slot_index IS NULL)
        ),
    CONSTRAINT fk_battle_pending_actions_battle
        FOREIGN KEY (battle_id) REFERENCES battles(battle_id) ON DELETE CASCADE,
    CONSTRAINT fk_battle_pending_actions_trainer
        FOREIGN KEY (trainer_id) REFERENCES trainers(trainer_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS battle_turn_history (
    battle_id                    INT NOT NULL,
    turn_number                  INT NOT NULL,
    summary                      TEXT NOT NULL,
    PRIMARY KEY (battle_id, turn_number),
    CONSTRAINT fk_battle_turn_history_battle
        FOREIGN KEY (battle_id) REFERENCES battles(battle_id) ON DELETE CASCADE
);