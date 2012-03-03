// Copyright © 2012 Esko Luontola <www.orfjackal.net>
// This software is released under the Apache License 2.0.
// The license text is at http://www.apache.org/licenses/LICENSE-2.0

package net.orfjackal.svngitsaclue

import org.eclipse.jgit.awtui.CommitGraphPane
import java.awt.BorderLayout
import org.eclipse.jgit.storage.file.FileRepositoryBuilder
import org.eclipse.jgit.revplot.PlotWalk
import javax.swing.{JScrollPane, JPanel, JFrame}

object GitCommitGraphSpike {
  def main(args: Array[String]) {

    val repository = new FileRepositoryBuilder()
            .setGitDir(GitRepositorySpike.RepositoryDir)
            .readEnvironment()
            .build()

    val walk = new PlotWalk(repository)
    walk.markStart(walk.parseCommit(repository.resolve("HEAD")))

    val commitGraph = new CommitGraphPane
    val commits = commitGraph.getCommitList
    commits.source(walk)
    commits.fillTo(Integer.MAX_VALUE)

    val root = new JPanel(new BorderLayout())
    root.add(new JScrollPane(commitGraph), BorderLayout.CENTER)

    val frame = new JFrame("JGit")
    frame.setContentPane(root)
    frame.pack()
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
    frame.setLocationByPlatform(true)
    frame.setVisible(true)
  }
}
