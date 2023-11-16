package jp.ac.it_college.std.s22012.pokeapi.database.repository

import androidx.annotation.WorkerThread
import jp.ac.it_college.std.s22012.pokeapi.database.dao.PokemonDao
import jp.ac.it_college.std.s22012.pokeapi.database.entity.Pokemon


/**
 * リポジトリパターンに則ってみているが、面倒なら不要かも
 */
class PokemonRepository(private val pokemonDao: PokemonDao) {
    fun findPokemonByGeneration(generation: Int): List<Pokemon> =
        pokemonDao.findByGeneration(generation)

    @WorkerThread
    suspend fun register(pokemon: List<Pokemon>) {
        pokemon.map(pokemonDao::insert)
    }
}