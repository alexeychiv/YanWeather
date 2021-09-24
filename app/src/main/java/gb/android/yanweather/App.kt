package gb.android.yanweather

import android.app.Application
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import gb.android.yanweather.room.HistoryDAO
import gb.android.yanweather.room.HistoryDataBase


class App : Application() {

    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }

    companion object {
        private var appInstance: App? = null
        private var db: HistoryDataBase? = null
        private const val DB_NAME = "HistoryDatabase.db"

        fun getHistoryDAO(): HistoryDAO {
            if (db == null) {
                if (appInstance != null) {
                    db = Room.databaseBuilder(
                        appInstance!!.applicationContext,
                        HistoryDataBase::class.java,
                        DB_NAME
                    )
                        .allowMainThreadQueries()
                        .addMigrations(MIGRATION_1_2)
                        .addMigrations(MIGRATION_2_3)
                        .addMigrations(MIGRATION_3_4)
                        .build()
                } else {
                    throw  IllegalStateException("appInstance==null")
                }
            }
            return db!!.historyDAO()
        }

        private val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(" ALTER TABLE HistoryEntity  RENAME COLUMN condition TO condition2")
            }
        }

        private val MIGRATION_2_3: Migration = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(" ALTER TABLE HistoryEntity  RENAME COLUMN condition2 TO condition")
            }
        }

        private val MIGRATION_3_4: Migration = object : Migration(3, 4) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(" ALTER TABLE HistoryEntity  ADD condition3 TEXT NOT NULL DEFAULT '' ")
            }
        }
    }


}