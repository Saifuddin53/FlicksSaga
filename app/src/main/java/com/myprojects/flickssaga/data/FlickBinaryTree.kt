package com.myprojects.flickssaga.data

import android.util.Log

class FlickBinaryTree {
    var root: Flick? = null

    // Function to insert a Flick into the binary tree
    fun insert(flick: Flick) {
        if (root == null) {
            root = flick
        } else {
            insertRec(root, flick)
        }
    }

    fun insertToLeft(parent: Flick, flick: Flick) {
        if (parent.leftFlick == null) {
            parent.leftFlick = flick
            flick.previous = parent
        } else {
            throw IllegalArgumentException("Left child already exists for this Flick")
        }
    }

    // Function to insert a Flick into the right of a given Flick
    fun insertToRight(parent: Flick, flick: Flick) {
        if (parent.rightFlick == null) {
            parent.rightFlick = flick
            flick.previous = parent
        } else {
            throw IllegalArgumentException("Right child already exists for this Flick")
        }
    }

    // Recursive function to insert a Flick into the binary tree
    private fun insertRec(current: Flick?, flick: Flick): Flick {
        if (current == null) {
            return flick
        }

        if (flick.id < current.id) {
            current.leftFlick = insertRec(current.leftFlick, flick)
        } else if (flick.id > current.id) {
            current.rightFlick = insertRec(current.rightFlick, flick)
        } else {
            // If ids are equal, we do not insert the duplicate
            return current
        }
        return current
    }

    // Function to search for a Flick by id
    fun search(id: Int): Flick? {
        return searchRec(root, id)
    }

    // Recursive function to search for a Flick by id
    private fun searchRec(current: Flick?, id: Int): Flick? {
        if (current == null || current.id == id) {
            return current
        }

        return if (id < current.id) {
            searchRec(current.leftFlick, id)
        } else {
            searchRec(current.rightFlick, id)
        }
    }

    // Function to traverse the tree in-order and apply a given action
    fun inOrderTraversal(action: (Flick) -> Unit) {
        inOrderTraversalRec(root, action)
    }

    // Recursive function for in-order traversal
    private fun inOrderTraversalRec(current: Flick?, action: (Flick) -> Unit) {
        if (current != null) {
            inOrderTraversalRec(current.leftFlick, action)
            action(current)
            inOrderTraversalRec(current.rightFlick, action)
        }
    }
}