package com.dhandroid2022.projetointegrador.ui.Home.HeroDetails.ui

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.dhandroid2022.projetointegrador.R
import com.dhandroid2022.projetointegrador.data.comicDTO.ComicDTO
import com.facebook.shimmer.ShimmerFrameLayout


class HeroDetailFragmentAdapter(
    private val context: Context,
    private val comicList: List<ComicDTO>,
    private val navController: NavController,
) : RecyclerView.Adapter<HeroDetailFragmentAdapter.ComicListViewHolder>() {

    inner class ComicListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val comicThumbnail: ImageView = itemView.findViewById(R.id.iv_comicthumbnail)
        val shimmerEffect: ShimmerFrameLayout = itemView.findViewById(R.id.shimmer_layout)
        val comicThumbnailShimmer: ImageView = itemView.findViewById(R.id.iv_comicthumbnail_shimmer)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): HeroDetailFragmentAdapter.ComicListViewHolder {
        val layoutInflater = LayoutInflater.from(context)
            .inflate(R.layout.fragment_hero_details_comic_list_item, parent, false)
        return ComicListViewHolder(layoutInflater)
    }

    override fun onBindViewHolder(
        holder: ComicListViewHolder,
        position: Int,
    ) {
        val comicTitle = comicList[position].title
        val comicId = comicList[position].id

        val comicUrl = comicList[position].thumbnail.getUrl()
        val thumbnailUrlWithS: String = StringBuilder(comicUrl).insert(4, "s").toString()

        insertImageFromUrl(thumbnailUrlWithS, holder.comicThumbnailShimmer, holder)

        holder.comicThumbnailShimmer.setOnClickListener {
            val action = HeroDetailFragmentDirections.actionHeroDetailFragmentToComicDetailFragment(
                thumbnailUrlWithS,
                comicTitle,
                comicId.toString(),
            )
            navController.navigate(action)
        }

    }

    override fun getItemCount(): Int = comicList.size

    private fun insertImageFromUrl(
        url: String,
        view: ImageView,
        holder: ComicListViewHolder,
    ) {
        Glide.with(context)
            .load(url)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean,
                ): Boolean {
                    holder.comicThumbnail.setImageResource(R.drawable.comictest)
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


}