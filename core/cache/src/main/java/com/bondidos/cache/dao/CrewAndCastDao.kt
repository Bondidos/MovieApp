package com.bondidos.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bondidos.cache.entity.CrewAndCastEntity

@Dao
interface CrewAndCastDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: CrewAndCastEntity)

    @Query("SELECT * FROM crew_and_cast_cache WHERE id = :id LIMIT 1")
    suspend fun get(id: Int): CrewAndCastEntity?
}