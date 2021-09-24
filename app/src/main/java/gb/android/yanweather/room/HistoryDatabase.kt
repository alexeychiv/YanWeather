package gb.android.yanweather.room

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = arrayOf(HistoryEntity::class), version = 4, exportSchema = false)
abstract class HistoryDataBase : RoomDatabase() {

    abstract fun historyDAO(): HistoryDAO
}