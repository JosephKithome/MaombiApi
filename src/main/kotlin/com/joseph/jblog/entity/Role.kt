package com.joseph.jblog.entity

import lombok.Getter
import lombok.Setter
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Getter
@Setter
@Entity
@Table(name = "roles")
class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id:Long? =null

    @Column(name ="name")
    var name: String?=null

        get() = field
    set(value) {field=value}

}