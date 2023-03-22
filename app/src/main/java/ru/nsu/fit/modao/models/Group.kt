package ru.nsu.fit.modao.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/*
  private Long id;
  private String groupName;
  private String description;
  private String uuid;
  private Integer typeGroup;
  private List<Long> userIdList;
 */

@Parcelize
data class Group(
    var id: Long? = null,
    val typeGroup: Int? = null,
    val description: String? = null,
    val groupName: String? = null,
    val uuid: String? = null,
    val userIdList: Array<Long>? = null
    //val name: String? = null
) : Parcelable