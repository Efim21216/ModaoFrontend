package ru.nsu.fit.modao.repository

import retrofit2.Response
import ru.nsu.fit.modao.models.Authorization
import ru.nsu.fit.modao.models.Expense
import ru.nsu.fit.modao.models.Group
import ru.nsu.fit.modao.models.User
import ru.nsu.fit.modao.utils.App
import ru.nsu.fit.modao.utils.Constants.Companion.AUTH

class Repository(private val app: App) {
    suspend fun login(user: User): Response<Authorization> {
        return app.api.login(user)
    }
    suspend fun getUser(): Response<User>{
        return app.api.getUser(AUTH + app.accessToken, app.userId)
    }
    suspend fun createGroup(group: Group): Response<Long>{
        return app.api.createGroup(AUTH + app.accessToken, group)
    }
    suspend fun createUser(user: User): Response<Long>{
        return app.api.createUser(user)
    }
    suspend fun getGroupExpenses(id: Long): Response<Array<Expense>>{
        return app.api.getGroupExpenses(AUTH + app.accessToken, id)
    }
    suspend fun createExpense(expense: Expense): Response<Long>{
        return app.api.createExpense(AUTH + app.accessToken, expense)
    }
    suspend fun addUserToGroup(userOrgId: Long, groupId: Long, userId: Long): Response<Long>{
        return app.api.addUserToGroup(AUTH + app.accessToken, userOrgId, groupId, userId)
    }
    suspend fun confirmEvent(eventId: Long): Response<Unit>{
        return app.api.confirmEvent(AUTH + app.accessToken, eventId)
    }
    suspend fun getUsersInGroup(groupId: Long): Response<Array<User>> {
        return app.api.getUsersInGroup(AUTH + app.accessToken, groupId)
    }
}