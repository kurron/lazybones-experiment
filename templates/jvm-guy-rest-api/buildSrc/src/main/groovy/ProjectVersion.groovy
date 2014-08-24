class ProjectVersion {
    Integer major
    Integer minor
    Integer patch
    Boolean release

    ProjectVersion( Integer major, Integer minor, Integer patch, Boolean release ) {
        this.major = major
        this.minor = minor
        this.patch = patch
        this.release = release
    }

    @Override
    String toString() {
        "${major}.${minor}.${patch}${release ? '.RELEASE' : '-SNAPSHOT'}"
    }
}
