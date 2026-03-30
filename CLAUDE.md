# CLAUDE.md

此檔案為 Claude Code（claude.ai/code）在此專案中運作時提供指引。

## 專案概述

這是一個以 Java 撰寫的資料結構與演算法自學專案，結合實作練習與中文學習筆記，跟隨教學影片與參考資料系統性地學習 DSA 基礎知識。

## 架構說明

**Java 原始碼**位於 `data-structures-algorithms/src/`：
- `main/java/com/practice/dsa/structures/` — 從零實作的資料結構：array、list、queue、stack、deque、priorityqueue、binarytree
- `main/java/com/practice/dsa/algorithms/` — 演算法實作：binarysearch、recursion（single/multi）
- `main/java/com/practice/dsa/leetcode/` — LeetCode 題解，依主題分類
- `test/java/com/practice/dsa/` — JUnit 5 測試，目錄結構與主原始碼對應

**學習筆記**位於頂層的中文命名目錄：
- `一、初識算法/` — 演算法基礎（複雜度、二分查找、遞迴）
- `二、基础数据结构/` — 資料結構筆記，附對應的 `images/` 子目錄
