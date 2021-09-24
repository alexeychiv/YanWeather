package gb.android.yanweather.room

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
    val temperature: Int,
    val condition: String,
    val condition3: String = "",
)