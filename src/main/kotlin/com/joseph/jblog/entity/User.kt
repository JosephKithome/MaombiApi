package com.joseph.jblog.entity

import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import javax.persistence.*


@Data // used to automatically add  getters and setters
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "users", uniqueConstraints = [UniqueConstraint(columnNames = ["username","email"])])
class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    @Column(name="name")
    var name: String? = null

    var username: String? = null
    var email: String? = null
    var password: String?=null


    //References the other table and FetchType eager is used to
    // for retrieving the roles time we fetch the user
    //Handling many to many relation and creating a child table
    @ManyToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinTable(
        name = "user_roles",
        joinColumns = [JoinColumn(name =  "user_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id")])
    var roles: Set<Role>? = null

}