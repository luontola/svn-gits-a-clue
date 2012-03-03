// Copyright © 2012 Esko Luontola <www.orfjackal.net>
// This software is released under the Apache License 2.0.
// The license text is at http://www.apache.org/licenses/LICENSE-2.0

package net.orfjackal.svngitsaclue

import java.io.File
import org.eclipse.jgit.storage.file.FileRepositoryBuilder
import org.eclipse.jgit.api.Git
import org.eclipse.jgit.revwalk.RevWalk
import scala.collection.JavaConversions._

object GitRepositorySpike {
  val RepositoryDir = new File("C:/DEVEL/Let's Play TDD/lets_play_tdd/.git")

  def main(args: Array[String]) {

    val repository = new FileRepositoryBuilder()
            .setGitDir(RepositoryDir)
            .readEnvironment() // scan environment GIT_* variables
            .findGitDir() // scan up the file system tree
            .build()

    val headId = repository.resolve("HEAD")
    println("HEAD ID: " + headId)
    println()

    val masterRef = repository.getRef("refs/heads/master")
    println("master ref: " + masterRef)
    println()

    val walk = new RevWalk(repository)

    val commit = walk.parseCommit(headId)
    println("HEAD commit: " + commit)
    println("- getId: " + commit.getId)
    println("- getName: " + commit.getName)
    println("- getAuthorIdent: " + commit.getAuthorIdent)
    println("- getCommitterIdent: " + commit.getCommitterIdent)
    println("- getCommitTime: " + commit.getCommitTime)
    println("- getEncoding: " + commit.getEncoding)
    println("- getFooterLines: " + commit.getFooterLines)
    println("- getFullMessage: " + commit.getFullMessage)
    println("- getParentCount: " + commit.getParentCount)
    println("- getParents: " + commit.getParents.toList)
    println("- getShortMessage: " + commit.getShortMessage)
    println("- getTree: " + commit.getTree)
    println("- getType: " + commit.getType)
    println("- getRawBuffer: " + new String(commit.getRawBuffer, commit.getEncoding))
    println()

    val git = new Git(repository)
    val commits = git.log().call()
    for (commit <- commits) {
      println(commit)
      println("\t" + commit.getAuthorIdent)
      println("\t" + commit.getShortMessage)
    }
  }
}
