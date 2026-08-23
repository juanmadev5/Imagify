package com.jmdev.app.imagify.domain.usecase

import com.jmdev.app.imagify.domain.repository.PhotoRepository
import com.jmdev.app.imagify.model.photo.User
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class GetUserProfileUseCaseTest {

    private val repository = mock(PhotoRepository::class.java)
    private lateinit var useCase: GetUserProfileUseCase

    @Before
    fun setUp() {
        useCase = GetUserProfileUseCase(repository)
    }

    @Test
    fun `returns user profile from repository when found`() = runTest {
        val user = User(id = "1", username = "juanma", name = "Juan Manuel")
        `when`(repository.getUserProfile("juanma")).thenReturn(user)

        val result = useCase("juanma")

        assertEquals(user, result)
    }

    @Test
    fun `returns null when repository has no profile for username`() = runTest {
        `when`(repository.getUserProfile("ghost")).thenReturn(null)

        val result = useCase("ghost")

        assertNull(result)
    }
}
