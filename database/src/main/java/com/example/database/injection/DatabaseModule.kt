package com.example.database.injection

import android.content.Context
import androidx.room.Room
import com.example.database.AppDatabase
import com.example.database.constants.DatabaseNames
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideCharacterDao(db: AppDatabase) = db.getCharacterDao()

    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, AppDatabase::class.java,
            DatabaseNames.MAIN_DATABASE_NAME).build()
}
