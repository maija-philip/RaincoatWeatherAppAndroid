package xyz.maija.raincoat.data.repositories

import android.app.Application
import kotlinx.coroutines.flow.Flow
import xyz.maija.raincoat.data.AppDatabase
import xyz.maija.raincoat.data.dao.UserDao
import xyz.maija.raincoat.data.entities.User


/*
    Handles the getting and retrieving of User data from the database
 */
class UserRepository(application: Application) {

    private var userDao: UserDao

    init {
        val database = AppDatabase.getDatabase(application)
        userDao = database.userDao()
    }

    fun readAllCustomers(): Flow<List<User>> {
        return userDao.fetchAllCustomer()
    }

    suspend fun insertCustomer(customer: User) {
        userDao.insertCustomer(customer)
    }

    suspend fun deleteCustomer() {
        userDao.deleteCustomer()
    }

} // CustomerRepository