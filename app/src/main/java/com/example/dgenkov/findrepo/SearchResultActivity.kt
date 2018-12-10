package com.example.dgenkov.findrepo

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import com.squareup.picasso.Picasso
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SearchResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)

        val searchTerm = intent.getStringExtra("searchTerm")
        println(searchTerm)

        val callback = object : Callback<GitHubSearchResult> {
            override fun onResponse(call: Call<GitHubSearchResult>, response: Response<GitHubSearchResult>) {
              val result =  response?.body()
                if(result != null) {
                    for (repo in result!!.items) {
                        println(repo.html_url)
                    }
                    val listView = findViewById<ListView>(R.id.listViewRepo)
                    listView.setOnItemClickListener { adapterView, view, i, l ->
                        val selectedRepo = result.items[i]
                        //Open in browser
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(selectedRepo.html_url))
                        startActivity(intent)
                    }
                    val adapter = RepoAdapter(this@SearchResultActivity,android.R.layout.simple_list_item_1,result!!.items)
                    listView.adapter = adapter
                }
            }
            override fun onFailure(call: Call<GitHubSearchResult>, t: Throwable) {
                println("failure")
            }
        }
        val retriever = GitHubRetriever()
        retriever.getRepos(callback, searchTerm)
    }
}

class RepoAdapter(context: Context, resource: Int, objects: List<Repo>?) :
    ArrayAdapter<Repo>(context, resource, objects) {
    override fun getCount(): Int {
        return super.getCount()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflator = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val repoView = inflator.inflate(R.layout.repo_list_layout,parent,false)
        val textView = repoView.findViewById<TextView>(R.id.repoTitleTextView)
        val repo = getItem(position)
        textView.text = repo.full_name
        val imageView = repoView.findViewById<ImageView>(R.id.repoImageView)
        Picasso.get().load(Uri.parse(repo.owner.avatar_url)).into(imageView)
        return repoView
    }

}
