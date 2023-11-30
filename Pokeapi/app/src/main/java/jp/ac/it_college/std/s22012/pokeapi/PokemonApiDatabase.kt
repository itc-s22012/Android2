package jp.ac.it_college.std.s22012.pokeapi

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import jp.ac.it_college.std.s22012.pokeapi.database.dao.PokemonDao
import jp.ac.it_college.std.s22012.pokeapi.database.entity.Pokemon
import kotlinx.coroutines.CoroutineScope

/**
 * とりあえず、以前のチュートリアルを見返して実装してみた。
 * もしかしたら今回のアプリでは不要な部分があるかも。
 */
@Database(
    entities = [
        Pokemon::class,
    ],
    version = 1
)
abstract class PokeApiDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao

    companion object {
        @Volatile
        private var INSTANCE: PokeApiDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): PokeApiDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PokeApiDatabase::class.java,
                    "pokeapi_database"
                )
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}