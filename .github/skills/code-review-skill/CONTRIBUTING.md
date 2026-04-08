# Contributing to AI Code Review Guide

Thank you for your interest in contributing! This document provides guidelines for contributing to this Claude Code Skill project.

## Claude Code Skill Development Standards

This project is a Claude Code Skill. Contributors must follow the standards below.

### Directory Structure

```
ai-code-review-guide/
├── SKILL.md                    # Required: main file (always loaded)
├── README.md                   # Project documentation
├── CONTRIBUTING.md             # Contributing guide (this file)
├── LICENSE                     # License
├── reference/                  # Detailed guides loaded on demand
│   ├── react.md
│   ├── vue.md
│   ├── rust.md
│   ├── typescript.md
│   ├── python.md
│   ├── c.md
│   ├── cpp.md
│   ├── common-bugs-checklist.md
│   ├── security-review-guide.md
│   └── code-review-best-practices.md
├── assets/                     # Templates and quick references
│   ├── review-checklist.md
│   └── pr-review-template.md
└── scripts/                    # Utility scripts
    └── pr-analyzer.py
```

### Frontmatter Standards

SKILL.md must include YAML frontmatter:

```yaml
---
name: skill-name
description: |
  Feature description. Trigger conditions.
  Use when [specific use case].
allowed-tools: ["Read", "Grep", "Glob"]  # Optional: restrict tool access
---
```

#### Required Fields

| Field | Description | Constraints |
|-------|-------------|-------------|
| `name` | Skill identifier | Lowercase letters, digits, and hyphens; max 64 characters |
| `description` | Functionality and activation conditions | Max 1024 characters; must include "Use when" |

#### Optional Fields

| Field | Description | Example |
|-------|-------------|----------|
| `allowed-tools` | Restrict tool access | `["Read", "Grep", "Glob"]` |

### Naming Conventions

**Skill name rules:**
- Use only lowercase letters, digits, and hyphens (kebab-case)
- Maximum 64 characters
- Avoid underscores or uppercase letters

```
✅ Correct: code-review-excellence, typescript-advanced-types
❌ Incorrect: CodeReview, code_review, TYPESCRIPT
```

**File naming rules:**
- Reference files use lowercase: `react.md`, `vue.md`
- Multi-word files use hyphens: `common-bugs-checklist.md`

### Description Writing Standards

The description must include two parts:

1. **Feature statement**: Specifically describe what the Skill can do
2. **Trigger conditions**: Start with "Use when" and explain when it should be activated

```yaml
# ✅ Correct example
description: |
  Provides comprehensive code review guidance for React 19, Vue 3, Rust,
  TypeScript, Java, Python, and C/C++.
  Helps catch bugs, improve code quality, and give constructive feedback.
  Use when reviewing pull requests, conducting PR reviews, establishing
  review standards, or mentoring developers through code reviews.

# ❌ Incorrect example (too vague, missing trigger conditions)
description: |
  Helps with code review.
```

### Progressive Disclosure

Claude only loads supporting files when needed; it does not load everything at once.

#### File Responsibility Breakdown

| File | When Loaded | Content |
|------|-------------|----------|
| `SKILL.md` | Always loaded | Core principles, quick index, when to use |
| `reference/*.md` | Loaded on demand | Detailed guides for languages/frameworks |
| `assets/*.md` | When explicitly needed | Templates, checklists |
| `scripts/*.py` | When explicitly directed | Utility scripts |

#### Content Organization Principles

**SKILL.md** (~200 lines or fewer):
- Overview: 2-3 sentences describing the purpose
- Core principles and methodology
- Language/framework index table (linking to reference/)
- When to use this Skill

**reference/*.md** (detailed content):
- Complete code examples
- All best practices
- Review Checklist
- Edge cases and pitfalls

### File Reference Standards

When referencing other files in SKILL.md:

```markdown
# ✅ Correct: use Markdown link format
| **React** | [React Guide](reference/react.md) | Hooks, React 19, RSC |
| **Vue 3** | [Vue Guide](reference/vue.md) | Composition API |

See [React Guide](reference/react.md) for the complete guide.

# ❌ Incorrect: using code block format
Refer to the `reference/react.md` file.
```

**Path rules:**
- Use relative paths (relative to the Skill directory)
- Use forward slashes `/`, not backslashes
- No `./` prefix needed

---

## Contribution Types

### Adding New Language Support

1. Create a new file in the `reference/` directory (e.g., `go.md`)
2. Follow this structure:

```markdown
# [Language] Code Review Guide

> Brief description, one sentence covering what is included.

## Table of Contents
- [Topic 1](#topic-1)
- [Topic 2](#topic-2)
- [Review Checklist](#review-checklist)

---

## Topic 1

### Subtopic

```[language]
// ❌ Bad pattern - explain why it is bad
bad_code_example()

// ✅ Good pattern - explain why it is good
good_code_example()
```

---

## Review Checklist

### Category 1
- [ ] Check item 1
- [ ] Check item 2
```

3. Add a link in the `SKILL.md` index table
4. Update the `README.md` statistics

### Adding Framework Patterns

1. Make sure to reference the official documentation
2. Include version numbers (e.g., "React 19", "Vue 3.5+")
3. Provide runnable code examples
4. Add corresponding checklist items

### Improving Existing Content

- Fix spelling or grammar errors
- Update outdated patterns (note version changes)
- Add edge case examples
- Improve the clarity of code examples

---

## Code Example Standards

### Format Requirements

```markdown
// ❌ Problem description - explain why this approach is bad
problematic_code()

// ✅ Recommended approach - explain why this approach is better
recommended_code()
```

### Quality Standards

- Examples should be based on real-world scenarios; avoid artificial constructions
- Show both the problem and the solution
- Keep examples concise and focused
- Include necessary context (import statements, etc.)

---

## Submission Process

### Issue Reporting

- Use GitHub Issues to report problems or suggestions
- Provide clear descriptions and examples
- Tag the relevant language/framework

### Pull Request Process

1. Fork the repository
2. Create a feature branch: `git checkout -b feature/add-go-support`
3. Make your changes
4. Commit (see commit format below)
5. Push to your fork: `git push origin feature/add-go-support`
6. Create a Pull Request

### Commit Message Format

```
type: short description

Detailed explanation (if needed)

- Specific change 1
- Specific change 2
```

**Types:**
- `feat`: New feature or content
- `fix`: Bug fix
- `docs`: Documentation-only changes
- `refactor`: Refactoring (no functionality change)
- `chore`: Maintenance work

**Example:**
```
feat: Add Go language code review guide

- Added reference/go.md
- Covers error handling, concurrency, and interface design
- Updated SKILL.md index table
```

---

## Skill Design Principles

### Single Responsibility

Each Skill focuses on one core capability. This Skill focuses on **code review** and should not be expanded to include:
- Code generation
- Project initialization
- Deployment configuration

### Version Management

- Note framework/language versions in reference files
- Describe version changes in commits when updating
- Outdated content should be updated, not deleted (unless fully deprecated)

### Content Quality

- All recommendations should be backed by evidence (official documentation, best practices)
- Avoid subjective preferences (such as code style); focus on objective issues
- Prioritize coverage of common pitfalls and security issues

---

## FAQ

### Q: How do I test my changes?

Copy the modified Skill to the `~/.claude/skills/` directory, then test it in Claude Code:
```bash
cp -r ai-code-review-guide ~/.claude/skills/code-review-excellence
```

### Q: Should I update SKILL.md or a reference file?

- **SKILL.md**: Only modify the index table or core principles
- **reference/*.md**: Add or update specific language or framework content

### Q: How should I handle outdated content?

1. Note the version change (e.g., "React 18 → React 19")
2. Keep the old version's content (if users may still be using it)
3. Update the related items in the checklist

---

## Questions

If you have any questions, feel free to ask in GitHub Issues.
