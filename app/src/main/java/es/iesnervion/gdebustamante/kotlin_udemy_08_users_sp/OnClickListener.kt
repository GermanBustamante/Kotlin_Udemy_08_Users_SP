package es.iesnervion.gdebustamante.kotlin_udemy_08_users_sp

//Interface para eventos del adapter
interface OnClickListener {
    fun onClick(user : User, position : Int)
}