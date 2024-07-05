package com.myprojects.flickssaga.repositories

import com.myprojects.flickssaga.data.Flick
import com.myprojects.flickssaga.data.FlickBinaryTree

class FlickRepository {


    val flicks = mutableListOf<Flick>(
        Flick(
            id = 1,
            videoUrl = "https://user-images.githubusercontent.com/90382113/170887700-e405c71e-fe31-458d-8572-aea2e801eecc.mp4",
            title = "Title 1",
            description = "Description 1",
        ),
        Flick(
            id = 2,
            videoUrl = "https://user-images.githubusercontent.com/90382113/170885742-d82e3b59-e45a-4fcf-a851-fed58ff5a690.mp4",
            title = "Title 2",
            description = "Description 2",
        ),
        Flick(
            id = 3,
            videoUrl = "https://user-images.githubusercontent.com/90382113/170888784-5d9a7623-10c8-4ca2-8585-fdb29b2ed037.mp4",
            title = "Title 3",
            description = "Description 3",
        ),
        Flick(
            id = 4,
            videoUrl = "https://user-images.githubusercontent.com/90382113/170889265-7ed9a56c-dd5f-4d78-b453-18b011644da0.mp4",
            title = "Title 4",
            description = "Description 4",
        ),
        Flick(
            id = 5,
            videoUrl = "https://user-images.githubusercontent.com/90382113/170890384-43214cc8-79c6-4815-bcb7-e22f6f7fe1bc.mp4",
            title = "Title 5",
            description = "Description 5",
        )
    )
    val flicksBinaryTree = insertFlicksBinaryTree()

    fun insertFlicksBinaryTree(): FlickBinaryTree{
        val flickBinaryTree = FlickBinaryTree()

        flickBinaryTree.root = flicks[0]
        flickBinaryTree.insertToLeft(flicks[0], flicks[1])
        flickBinaryTree.insertToRight(flicks[0], flicks[2])
        flickBinaryTree.insertToLeft(flicks[1], flicks[3])
        flickBinaryTree.insertToRight(flicks[1], flicks[4])

        return flickBinaryTree
    }

    fun  insertRoot(flick: Flick): FlickBinaryTree {
        flicksBinaryTree.root = flick
        flicks.add(flick)
        return flicksBinaryTree
    }

    fun insertToLeft(parent: Flick, flick: Flick): FlickBinaryTree {
        flicksBinaryTree.insertToLeft(parent, flick)
        flicks.add(flick)
        return flicksBinaryTree
    }

    fun insertToRight(parent: Flick, flick: Flick): FlickBinaryTree {
        flicksBinaryTree.insertToRight(parent, flick)
        flicks.add(flick)
        return flicksBinaryTree
    }

    fun searchFlick(id: Int): Flick? {
        return flicksBinaryTree.search(id)
    }

}
