package com.doni.kotlinrestreturnman.repository

import com.doni.kotlinrestreturnman.entity.Item
import org.springframework.data.jpa.repository.JpaRepository

interface ItemRepository : JpaRepository<Item, String> {
}