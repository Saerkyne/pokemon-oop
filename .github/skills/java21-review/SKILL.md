---
name: java21-review
description: "Java 21 code review and project planning for junior developers. Use when: reviewing Java code, Java code review, Java 21 best practices, modernizing Java code, planning a Java project, Java project guide, Java architecture planning, project outline, flow diagram, implementation plan. Produces structured review reports with educational explanations and fix examples. Can also generate project guides with Mermaid diagrams and plain English walkthroughs."
argument-hint: "Paste code, a file path, or describe a Java project to review or plan"
---

# Java 21 Code Review & Project Guide

A dual-purpose skill for junior Java developers: **review existing code** with educational feedback, or **plan new projects** with architecture guides and flow diagrams.

## When to Use

### Code Review Mode (default)
- Reviewing Java files or snippets for quality, security, and modern practices
- Learning why something is wrong and how to fix it
- Checking whether code uses Java 21 features effectively
- Auditing for security vulnerabilities, performance issues, or style problems

### Project Guide Mode (on request — triggered by "project guide", "plan", "outline", "architecture", or "flow diagram")
- Planning a new Java 21 project from a description
- Getting an architecture breakdown and component diagram
- Understanding what to build first and what pitfalls to watch for
- Getting tool/technology recommendations (databases, libraries, formats) with Java as the center

## Code Review Procedure

When the user provides Java code (file paths, snippets, or describes code to review):

### Step 1: Gather Context

Read all relevant files. Understand the project structure, dependencies, and purpose before reviewing. If a `pom.xml` or `build.gradle` exists, check the Java version and dependencies.

### Step 2: Analyze Against the Review Checklist

Work through the [Review Checklist](./references/review-checklist.md) systematically. Every issue found must include:

1. **What** — the specific problem
2. **Why** — why it matters (educational explanation a junior dev can learn from)
3. **How to fix** — a concrete code example showing the fix
4. **Severity** — how urgent it is

### Step 3: Produce Structured Report

Output the review in this format:

```markdown
# Java 21 Code Review — [File/Component Name]

**Review Date:** [date]
**Scope:** [what was reviewed]
**Java Version:** [detected or assumed version]

---

## Summary

[2-3 sentence overview: what's good, what needs work, overall assessment]

---

## Issues

### 🔴 [Blocking] Issue Title

**File:** `path/to/File.java` | **Line(s):** X–Y

**What's wrong:**
[Clear description of the problem]

**Why this matters:**
[Educational explanation — what could go wrong, what principle is violated, why Java does it this way]

**Current code:**
```java
// the problematic code
```

**Recommended fix:**
```java
// the corrected code with comments explaining key changes
```

---

### 🟡 [Important] Issue Title
[Same structure as above]

### 🟢 [Suggestion] Issue Title
[Same structure as above]

### 📚 [Learning] Educational Note Title
[Same structure — used for "this works but here's a better Java 21 way"]

---

## What's Done Well

[Specific praise — name the good patterns, explain WHY they're good so the dev reinforces those habits]

---

## Recommended Next Steps

[Prioritized list of what to fix first and why]
```

### Severity Levels

| Level | Icon | Meaning | Action |
|-------|------|---------|--------|
| Blocking | 🔴 | Bug, security flaw, or crash risk. Will cause problems. | Must fix before merge/deploy |
| Important | 🟡 | Code smell, performance concern, or missing best practice. Works but fragile. | Should fix soon |
| Suggestion | 🟢 | Style, readability, or minor improvement. Not broken. | Nice to have |
| Learning | 📚 | Not an issue — an opportunity to teach a Java 21 feature or pattern. | Educational only |

### Review Focus Areas

Consult the [Review Checklist](./references/review-checklist.md) for detailed checks in each area:

1. **Java 21 Modern Features** — Are there opportunities to use records, sealed classes, pattern matching, text blocks, virtual threads, sequenced collections, or enhanced switch?
2. **Security** — SQL injection, input validation, credential handling, deserialization, path traversal
3. **Performance & Efficiency** — Algorithm complexity, resource management, collection choices, unnecessary object creation
4. **Code Style & Readability** — Naming, encapsulation, method length, single responsibility, clarity
5. **Education** — Every issue must teach something. Explain the "why" at a level appropriate for someone learning Java

---

## Project Guide Procedure

When the user describes a project and asks for a guide, plan, outline, or architecture:

### Step 1: Understand the Project

Ask clarifying questions ONLY if the description is too vague to produce useful guidance. Otherwise, proceed with reasonable assumptions and state them.

### Step 2: Produce the Guide

Follow the structure in [Project Guide Reference](./references/project-guide.md). The guide must include:

1. **Project Overview** — restate the goal in clear terms
2. **Recommended Java 21 Features** — which modern features fit this project and why
3. **Architecture / Component Breakdown** — layers, packages, key classes
4. **Flow Diagram** — both Mermaid and plain English walkthrough
5. **Implementation Order** — what to build first, second, third, and why
6. **Potential Pitfalls** — common mistakes for this type of project
7. **Tool & Technology Recommendations** — databases, libraries, formats, other languages IF relevant (Java stays central)

### Important Constraints for Project Guides

- **Do NOT generate implementation code.** The guide is an outline and plan, not a codebase.
- **DO use Mermaid diagrams** for visual architecture and flow — render them in fenced `mermaid` code blocks.
- **DO provide a plain English walkthrough** alongside every diagram for accessibility.
- **Keep Java as the center.** Other tools (databases, JSON libraries, etc.) are supporting cast.
