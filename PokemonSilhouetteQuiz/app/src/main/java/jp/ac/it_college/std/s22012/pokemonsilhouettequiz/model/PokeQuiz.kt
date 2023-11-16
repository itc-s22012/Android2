package jp.ac.it_college.std.s22012.pokemonsilhouettequiz.model

/**
 * 1問分の問題データ
 */
data class PokeQuiz(
    val imageUrl: String,
    val choices: List<String>,
    val correct: String
)