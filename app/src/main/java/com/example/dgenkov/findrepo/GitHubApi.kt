package com.example.dgenkov.findrepo


import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubService {
    @GET("search/repositories?q=eggs")
    fun searcRepos(@Query("q") q: String) : Call<GitHubSearchResult>
}

class GitHubSearchResult(val items: List<Repo>)
class Repo(val full_name: String, val owner: GitHubUser, val html_url: String)
class GitHubUser(val avatar_url: String)

class GitHubRetriever {
    val service: GitHubService

    init {
        val retrofit = Retrofit.Builder().baseUrl("https://api.github.com").addConverterFactory(GsonConverterFactory.create()).build()
        service = retrofit.create(GitHubService::class.java)
    }

    fun getRepos(callback: Callback<GitHubSearchResult>, searchTerm: String) {
        var searchT = searchTerm
        val call = service.searcRepos(searchT)
        call.enqueue(callback)
    }

}