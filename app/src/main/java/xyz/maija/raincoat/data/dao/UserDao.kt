package xyz.maija.raincoat.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import xyz.maija.raincoat.data.entities.User


/**
 * Data Access Object to access the User from the database
 */
// one data access object for each entity (usually, with joins you may have a combined one)
@Dao
interface UserDao {

    @Query("SELECT * FROM User") // must be suspend functions or flow (publish/subscribe)
    fun fetchAllCustomer(): Flow<List<User>> // flow auto on coroutine

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCustomer(customer: User)

    @Query("DELETE FROM User")
    suspend fun deleteCustomer()

} // UserDao