package com.dhandroid2022.projetointegrador.ui.Home.HeroList.ui

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.dhandroid2022.projetointegrador.R
import com.dhandroid2022.projetointegrador.data.heroDTO.HeroDTO
import com.dhandroid2022.projetointegrador.ui.Home.HomeFragmentDirections
import com.facebook.shimmer.ShimmerFrameLayout

class HeroesListAdapter(
    private val context: Context?,
    val heroesList: MutableList<HeroDTO>,
    private val navController: NavController,
) : RecyclerView.Adapter<HeroesListAdapter.HeroViewHolder>() {

    var adapterList = heroesList

    inner class HeroViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val heroName: TextView = itemView.findViewById(R.id.heroes_list_item_name)
        val heroThumbnail: ImageView = itemView.findViewById(R.id.heroes_list_item_thumbnail)
        val shimmerEffect: ShimmerFrameLayout = itemView.findViewById(R.id.shimmer_layout)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): HeroViewHolder {
        val layoutInflater =
            LayoutInflater.from(context).inflate(R.layout.fragment_heroes_list_item, parent, false)
        return HeroViewHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: HeroViewHolder, position: Int) {
        val heroName = heroesList[position].name
        val heroID = heroesList[position].id
        val heroDescription = heroesList[position].description

        val thumbnailUrl: String = heroesList[position].thumbnail.getUrl()
        val thumbnailUrlWithS: String = StringBuilder(thumbnailUrl).insert(4, "s").toString()

        holder.heroName.text = heroName
        insertImageFromUrl(thumbnailUrlWithS, holder.heroThumbnail, holder)

        holder.heroThumbnail.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToHeroDetailFragment(
                thumbnailUrlWithS,
                heroName,
                heroID.toString(),
                heroDescription,
            )
            navController.navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return heroesList.size
    }

    private fun insertImageFromUrl(
        url: String,
        view: ImageView,
        holder: HeroViewHolder,
    ) {
        Glide.with(context!!)
            .load(url)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean,
                ): Boolean {
                    holder.heroThumbnail.setImageResource(R.drawable.comictest)
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean,
                ): Boolean {
                    holder.shimmerEffect.hideShimmer()
                    return false
                }
            })
            .into(view)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addData(tempList: List<HeroDTO>) {
        for (hero in tempList) {
            heroesList.add(hero)
        }
        notifyDataSetChanged()
    }

}