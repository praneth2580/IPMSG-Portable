package com.example.ipmsg20.room

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import java.net.InetAddress

@Entity
data class Message(

    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "mid") val mid: Int,
    @ColumnInfo(name = "index") val index: String,
    @ColumnInfo(name = "sender") val sender: String,
    @ColumnInfo(name = "sender_group") val group: String,
    @ColumnInfo(name = "address") val address: String

)

@Dao
interface MessageDao {

    @Query("select * from Message")
    fun getAll(): List<Message>

    @Query("select * from Message where sender = :sender")
    fun getBySender(sender: String): List<Message>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMessage(message: Message): Long

    @Delete
    fun deleteMessage(message: Message)

}

class MessageRepo {
    suspend fun insertMessage(db: Database, message: Message) : Long {
        val dao = db.messageDao();
        return dao.addMessage(message)
    }
}