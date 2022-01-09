package es.iesnervion.gdebustamante.kotlin_udemy_08_users_sp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import es.iesnervion.gdebustamante.kotlin_udemy_08_users_sp.databinding.ItemUserAltBinding
import es.iesnervion.gdebustamante.kotlin_udemy_08_users_sp.databinding.ItemUserBinding

class UserAdapter(private val users: List<User>, private val listener: OnClickListener) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    private lateinit var context : Context

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemUserAltBinding.bind(view) //ItemUserBinding es menos potente

        fun setListener(user : User, position: Int){
            binding.root.setOnClickListener { (listener.onClick(user, position)) }
        }
    }

    //Infla la lista xaml y esta lista pasa con el viewHolder para su correspondiente configuración
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_user_alt, parent, false)

        return ViewHolder(view)
    }

    //Esto rellena la lista
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = users[position]
        with(holder){
            setListener(user, position)
            binding.tvOrder.text = (position +1).toString()
            binding.tvName.text = user.getFullName()
            Glide.with(context)
                .load(user.url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .circleCrop()//Pones la imagen en circulo
                .into(binding.imgPhoto)
        }
    }

    //Devolvemos la longitud de la vista
    override fun getItemCount(): Int = users.size

}