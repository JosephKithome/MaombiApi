package com.joseph.jblog.entity

import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import javax.persistence.*

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "comments")
class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(name = "name")
    var name: String? = null

    @Column(name = "email")
    var email: String? = null

    @Column(name = "body")
    var body: String? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="prayer_id", nullable = false)
    var prayer: Prayer? = null


}