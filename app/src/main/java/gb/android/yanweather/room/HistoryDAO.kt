package gb.android.yanweather.room

import androidx.room.*


@Dao
interface HistoryDAO {

    @Query("SELECT * FROM HistoryEntity")
    fun all(): List<HistoryEntity>

    @Query("SELECT * FROM HistoryEntity WHERE name LIKE :name")
    fun getDataByWord(name: String): List<HistoryEntity>


    @Query("DELETE FROM HistoryEntity WHERE id=:idForDelete")
    fun deleteQ(idForDelete: Long)

    @Delete
    fun delete(entity: HistoryEntity)

    @Update
    fun update(entity: HistoryEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(entity: HistoryEntity)
}