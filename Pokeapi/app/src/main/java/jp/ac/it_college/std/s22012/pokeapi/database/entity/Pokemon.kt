package jp.ac.it_college.std.s22012.pokeapi.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * ポケモンのデータを表すエンティティ。必須。
 */
@Entity(tableName = "pokemon")
data class Pokemon(
    @PrimaryKey val id: Int,
    val generation: Int,
    val name: String,
    @ColumnInfo(name = "main_texture_url") val mainTextureUrl: String,
)