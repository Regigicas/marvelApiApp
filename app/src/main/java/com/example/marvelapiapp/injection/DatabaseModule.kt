package com.example.marvelapiapp.injection

import android.content.Context
import androidx.room.Room
import com.example.data.datasource.database.AppDatabase
import com.example.data.datasource.database.dao.CharacterDao
import com.example.domain.constant.DatabaseNames
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideCharacterDao(db: AppDatabase): CharacterDao = db.getCharacterDao()

    @Provides
    @Singleton
    fun providesAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java,
            DatabaseNames.MAIN_DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
}
