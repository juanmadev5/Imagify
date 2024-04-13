package com.jmdev.app.imagify.model.collectionphotoresult

import com.jmdev.app.imagify.model.collection.Collection
import com.jmdev.app.imagify.model.photo.Photo

data class CollectionPhotoResult(
    val photo: Photo?,
    val collection: Collection?
)