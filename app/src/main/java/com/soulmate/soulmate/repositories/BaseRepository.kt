package com.soulmate.soulmate.repositories

import com.soulmate.soulmate.api.errors.IErrorHandler


open class BaseRepository(protected val errorHandler: IErrorHandler)