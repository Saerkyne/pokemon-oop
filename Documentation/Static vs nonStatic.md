
# Static vs Non-Static Methods in TrainerCRUD and TeamCRUD

         * STATIC vs NON-STATIC METHODS — Explanation
         *============================================
         *
         *The compile error "Cannot make a static reference to the non-static method"
         * means you're calling a method using the CLASS name (e.g., TrainerCRUD.getTrainerByDiscordId(...))
         *but the method belongs to an INSTANCE (an object), not to the class itself.
         *
         *Think of it like this:
         * - A CLASS is a blueprint. "TrainerCRUD" describes what a TrainerCRUD object can do.
         *- An INSTANCE (object) is a specific thing built from that blueprint.
         *
         *STATIC methods belong to the blueprint itself. You call them on the class name:
         *     Math.sqrt(16)          // sqrt is static — no Math object needed
         *PokemonFactory.getSpeciesNames()  // static — works on the class directly
         *
         *NON-STATIC (instance) methods belong to a specific object. You must create
         * an object first, then call the method on that object:
         *TrainerCRUD crud = new TrainerCRUD();   // create an instance
         *     crud.getTrainerByDiscordId(discordId);  // call on the instance
         *
         * WHY does this distinction exist?
         *- Instance methods can access instance fields (data unique to each object).
         *   If TrainerCRUD stored a database connection as a field, each instance could
         *have its own connection.
         * - Static methods can't access instance fields — they don't know which object
         *you mean. They only have access to static (shared) data.
         *
         *SHOULD you make these methods static?
         * - If the method doesn't use any instance fields (no "this.something"), making
         *it static is fine and simpler. TrainerCRUD.getTrainerByDiscordId() and
         *   TeamCRUD.getDBTeamForTrainer() don't use instance state — they open a fresh
         *database connection each time. So making them static would be reasonable.
         * - However, if you later add connection pooling (e.g., a shared HikariDataSource
         *field), you'd want instance methods so each CRUD object can hold a reference
         *   to the data source. That's a design decision for later.
         *
         * TWO WAYS TO FIX:
         *
         * Option A — Create an instance (what we do below):
         *TrainerCRUD trainerCRUD = new TrainerCRUD();
         *     trainerCRUD.getTrainerByDiscordId(discordId);
         *
         * Option B — Make the methods static (change the method declaration):
         *public static Trainer getTrainerByDiscordId(long discordID) { ... }
         *   Then you can call TrainerCRUD.getTrainerByDiscordId(discordId) directly.
         *
         * We use Option A here to match how current slash command handlers already use these classes.
         */
