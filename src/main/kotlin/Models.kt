import kotlinx.serialization.Serializable

@Serializable
data class Request(
    var method: String? = null,
    var host: String? = null,
    var path: String? = null,
    var params: Map<String, String>? = null,
    var headers: Map<String, String>? = null
)

@Serializable
data class CityInfo(
    var id: String? = null,
    var city: String? = null,
    var state: String? = null,
    var pop: Int? = null,
    var loc: List<Double>? = null
)

@Serializable
data class Like(
    var id: String? = null,
    var date: Date? = null
)

@Serializable
data class Location(
    var type: Int? = null,
    var country: String? = null,
    var state: String? = null,
    var city: String? = null,
    var place: String? = null
)

@Serializable
data class Profile(
    var id: Long? = null,
    var login: String? = null,
    var avatar_url: String? = null,
    var gravatar_id: String? = null,
    var url: String? = null,
    var html_url: String? = null,
    var followers_url: String? = null,
    var following_url: String? = null,
    var gists_url: String? = null,
    var starred_url: String? = null,
    var subscriptions_url: String? = null,
    var organizations_url: String? = null,
    var repos_url: String? = null,
    var events_url: String? = null,
    var received_events_url: String? = null,
    var type: String? = null,
    var site_admin: Boolean? = null
)

@Serializable
data class Repo(
    var id: Long? = null,
    var name: String? = null,
    var full_name: String? = null,
    var owner: Profile? = null,
    var prvt: Boolean? = null,
    var html_url: String? = null,
    var description: String? = null,
    var fork: Boolean? = null,
    var url: String? = null,
    var forks_url: String? = null,
    var keys_url: String? = null,
    var collaborators_url: String? = null,
    var teams_url: String? = null,
    var hooks_url: String? = null,
    var issue_events_url: String? = null,
    var events_url: String? = null,
    var assignees_url: String? = null,
    var branches_url: String? = null,
    var tags_url: String? = null,
    var blobs_url: String? = null,
    var git_tags_url: String? = null,
    var git_refs_url: String? = null,
    var trees_url: String? = null,
    var statuses_url: String? = null,
    var languages_url: String? = null,
    var stargazers_url: String? = null,
    var contributors_url: String? = null,
    var subscribers_url: String? = null,
    var subscription_url: String? = null,
    var commits_url: String? = null,
    var git_commits_url: String? = null,
    var comments_url: String? = null,
    var issue_comment_url: String? = null,
    var contents_url: String? = null,
    var compare_url: String? = null,
    var merges_url: String? = null,
    var archive_url: String? = null,
    var downloads_url: String? = null,
    var issues_url: String? = null,
    var pulls_url: String? = null,
    var milestones_url: String? = null,
    var notifications_url: String? = null,
    var labels_url: String? = null,
    var releases_url: String? = null,
    var created_at: Date? = null,
    var updated_at: Date? = null,
    var pushed_at: Date? = null,
    var git_url: String? = null,
    var ssh_url: String? = null,
    var clone_url: String? = null,
    var svn_url: String? = null,
    var homepage: String? = null,
    var size: Int? = null,
    var stargazers_count: Int? = null,
    var watchers_count: Int? = null,
    var language: String? = null,
    var has_issues: Boolean? = null,
    var has_downloads: Boolean? = null,
    var has_wiki: Boolean? = null,
    var forks_count: Int? = null,
    var mirror_url: String? = null,
    var open_issues_count: Int? = null,
    var forks: Int? = null,
    var open_issues: Int? = null,
    var watchers: Int? = null,
    var default_branch: String? = null,
    var master_branch: String? = null,
    var fork_repos: List<Repo>? = null
)

@Serializable
data class UserNames(
    var nickname: String? = null,
    var displayName: String? = null,
    var givenName: String? = null,
    var familyName: String? = null,
    var middleName: String? = null,
    var prefix: String? = null,
    var suffix: String? = null
)

@Serializable
data class UserProfile(
    var id: String? = null,
    var profileViews: Long? = null,
    var email: String? = null,
    var name: UserNames? = null,
    var gender: Int? = null,
    var birthday: String? = null,
    var locations: List<Location>? = null,
    var image: String? = null,
    var cover: String? = null,
    var aboutMe: String? = null,
    var etag: String? = null,
    var active: Boolean? = null,
    var deleted: Boolean? = null,
    var lastAccess: Date? = null,
    var likes: List<Like>? = null
)