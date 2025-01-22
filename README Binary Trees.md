# TreeMap Implementations

## Overview
This project provides implementations of tree-based data structures (`BSTreeMap`, `RBTreeMap`) and includes utilities for visualizing and testing them. The `BinarySearchTreePrinter` adds functionality for generating ASCII representations of the trees, making it easier to debug and understand their structures.

## Key Features
### 1. **Binary Search Tree (`BSTreeMap`)**
- Implements a binary search tree (BST) for efficient key-value mapping.
- Core functionalities:
  - Insert, retrieve, and delete operations.
  - Traversals (preorder, inorder, postorder).
  - Height and size calculations.
  - ASCII tree visualization.

### 2. **Red-Black Tree (`RBTreeMap`)**
- Extends `BSTreeMap` with balancing properties:
  - Nodes are either red or black.
  - Ensures logarithmic height via rebalancing after insertions and deletions.
  - Includes rotation and fix-up methods for maintaining red-black properties.

### 3. **Tree Visualization**
- The `BinarySearchTreePrinter` generates an ASCII representation of the tree structure, displaying the hierarchy of nodes and their relationships.

### 4. **Test Cases**
- Comprehensive JUnit test cases for `BSTreeMap` and `RBTreeMap`.
- Validates:
  - Insertions, deletions, and edge cases.
  - Tree traversals and structure properties.
  - Height and search cost calculations.

### 5. **Tree Runner**
- A utility (`TreeRunner`) for testing and visualizing trees with command-line arguments.

## File Structure
### Source Files
1. **`BSTreeMap.java`**:
   - Core implementation of the binary search tree.
2. **`RBTreeMap.java`**:
   - Red-black tree implementation, extending `BSTreeMap`.

### Supporting Classes
1. **`Node.java`**:
   - Represents a generic node in the tree.
2. **`RBNode.java`**:
   - Extends `Node` with red-black properties (color).
3. **`Entry.java`**:
   - Encapsulates key-value pairs in the tree.
4. **`Pair.java`**:
   - Helper class for key-value pairs.
5. **`BinarySearchTreePrinter.java`**:
   - Generates ASCII representations of the tree for debugging and visualization.

### Interfaces
1. **`MyMap.java`**:
   - Defines the interface for map-like collections.

### Test Cases
1. **`BSTreeMapTestCases.java`**:
   - JUnit test cases for `BSTreeMap`.
2. **`RBTreeMapTestCases.java`**:
   - JUnit test cases for `RBTreeMap`.

### Utility
1. **`TreeRunner.java`**:
   - Facilitates testing and visualizing trees using command-line arguments.