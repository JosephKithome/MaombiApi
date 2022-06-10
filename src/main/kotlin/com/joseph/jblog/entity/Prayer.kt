package com.joseph.jblog.entity

import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import javax.persistence.*


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
    name = "posts", uniqueConstraints = [UniqueConstraint(columnNames = ["title"])])
class Prayer{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Column(name = "title")
    var title: String? = null

    @Column(name = "description")
    var description: String? =null

    @Column(name = "content")
    var content: String?=null

    //Define relationship here
    @OneToMany(mappedBy = "prayer", cascade = [CascadeType.ALL], orphanRemoval = true)
    var comments: Set<Comment>? = null
}
