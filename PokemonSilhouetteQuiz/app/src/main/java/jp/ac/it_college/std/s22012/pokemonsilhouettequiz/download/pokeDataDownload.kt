package jp.ac.it_college.std.s22012.pokemonsilhouettequiz.download

import jp.ac.it_college.std.s22012.pokemonsilhouettequiz.api.GamesGroup
import jp.ac.it_college.std.s22012.pokemonsilhouettequiz.database.PokeRoomDatabase
import jp.ac.it_college.std.s22012.pokemonsilhouettequiz.database.entity.Poke
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun pokeDataDownload (scope: CoroutineScope){
    scope.launch {
        withContext(Dispatchers.IO) {
            val data = pokeApi()
            PokeRoomDatabase.getDatabase(context).pokeDao().insertAll(data)
        }
    }
}
suspend fun pokeApi(): List<Poke> {
    val generation = GamesGroup.getGeneration()
}