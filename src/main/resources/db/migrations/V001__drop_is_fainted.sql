-- V001: Remove the is_fainted column from pokemon_instances.
--
-- Fainted state is now derived from current_hp in Java (currentHP <= 0).
-- Storing it as a separate column was a redundant second source of truth
-- that caused bugs (e.g. healToFull() forgetting to clear the flag).
-- See Code-Review-Model-Package.md issues MDL-1 and MDL-2.

ALTER TABLE pokemon_instances DROP COLUMN IF EXISTS is_fainted;
