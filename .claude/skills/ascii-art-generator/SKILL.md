---
name: ASCII Art Generator
description: Generates ASCII art from text input with optional decorative frames. Returns structured JSON output for easy piping and integration.
---
# ASCII Art Generator

A self-contained CLI tool that converts plain text into large block-letter ASCII art. Supports uppercase letters, digits, and common punctuation. Optionally wraps the output in a decorative frame with multiple style choices.

All output is structured JSON printed to stdout, making it easy to pipe into other tools or parse programmatically.

## How to Use

Run the script with Python 3, passing your text as a positional argument:

```bash
python3 .claude/skills/ascii-art-generator/ascii-art.py "YOUR TEXT"
```

### Options

| Flag | Description |
|---|---|
| `--frame`, `-f` | Wrap the ASCII art in a decorative frame |
| `--frame-style <style>`, `-s <style>` | Frame style: `single` (default), `double`, `bold`, `rounded` |
| `--padding <n>`, `-p <n>` | Horizontal padding inside the frame (default: 1) |
| `--help`, `-h` | Show usage information |

### JSON Output Schema

```json
{
  "text": "HELLO",
  "art": ["line1", "line2", "..."],
  "framed": false,
  "frameStyle": null,
  "width": 42,
  "height": 5
}
```

- `text` — the original input string
- `art` — array of strings, one per line of the ASCII art (or framed art)
- `framed` — whether a frame was applied
- `frameStyle` — the frame style used, or `null`
- `width` — character width of the widest line
- `height` — number of lines

## Examples

**Basic text:**
```bash
python3 .claude/skills/ascii-art-generator/ascii-art.py "HI"
```

**With a frame:**
```bash
python3 .claude/skills/ascii-art-generator/ascii-art.py --frame "HELLO"
```

**Double frame with extra padding:**
```bash
python3 .claude/skills/ascii-art-generator/ascii-art.py --frame --frame-style double --padding 3 "WOW"
```

**Pipe just the art lines (using jq):**
```bash
python3 .claude/skills/ascii-art-generator/ascii-art.py "OK" | jq -r '.art[]'
```

## Notes

- **Runtime:** Python 3 (no external dependencies — uses only stdlib)
- Input is automatically converted to uppercase (the font only defines uppercase glyphs)
- Unsupported characters are rendered as a blank column
- The script exits with code 1 and prints errors to stderr on invalid usage

