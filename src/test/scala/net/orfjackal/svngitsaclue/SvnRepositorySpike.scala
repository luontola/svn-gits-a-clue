// Copyright © 2012 Esko Luontola <www.orfjackal.net>
// This software is released under the Apache License 2.0.
// The license text is at http://www.apache.org/licenses/LICENSE-2.0

package net.orfjackal.svngitsaclue

import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory
import org.tmatesoft.svn.core.io.SVNRepositoryFactory
import org.tmatesoft.svn.core.wc.SVNWCUtil
import scala.collection.JavaConversions._
import org.tmatesoft.svn.core.{SVNLogEntry, SVNURL}
import org.joda.time.format.ISODateTimeFormat

object SvnRepositorySpike {
  def main(args: Array[String]) {
    DAVRepositoryFactory.setup()
    SVNRepositoryFactoryImpl.setup();
    FSRepositoryFactory.setup()

    val url = "http://scalatest.googlecode.com/svn/trunk/"
    val name = "anonymous"
    val password = "anonymous"
    val startRevision = 3000L
    val endRevision = -1L // HEAD

    val repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(url))
    val authManager = SVNWCUtil.createDefaultAuthenticationManager(name, password)
    repository.setAuthenticationManager(authManager)

    val logEntries = repository.log(Array(""), null, startRevision, endRevision, true, true).asInstanceOf[java.util.Collection[SVNLogEntry]]
    for (logEntry <- logEntries) {
      //println(logEntry)
      println("------------------")
      println()


      println("Revision: " + logEntry.getRevision)
      println("Author: " + logEntry.getAuthor)
      val juDate = logEntry.getDate
      val rawDate = logEntry.getRevisionProperties.getStringValue("svn:date")
      val jodaDateTime = ISODateTimeFormat.dateTimeParser.withOffsetParsed.parseDateTime(rawDate)
      println("Date: " + juDate + " / " + rawDate + " / " + jodaDateTime + " " + jodaDateTime.getMillisOfSecond)
      println("Message: " + logEntry.getMessage)
      println("Properties: " + logEntry.getRevisionProperties)
      println()

      println("Changed paths:")
      for (path <- logEntry.getChangedPaths.values) {
        //println(path)
        println(path.getType + " (" + path.getKind + ") " + path.getPath)
        if (path.getCopyPath != null) {
          println("\tcopy from " + path.getCopyPath + " @ " + path.getCopyRevision)
        }
      }
    }
  }
}
