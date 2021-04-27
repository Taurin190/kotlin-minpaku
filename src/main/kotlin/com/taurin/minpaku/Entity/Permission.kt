package com.taurin.minpaku.Entity

import lombok.Getter
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "permission")
data class Permission(
    @Id
    @Getter
    @Column(name = "permission_id")
    var permissionId: Int,
    @Getter
    var name: String,
    @Getter
    var description: String?
)
