#!/usr/bin/env python3
"""ASCII Art Generator — converts text to block-letter ASCII art with optional frames.

Usage:
    python3 ascii-art.py [options] <text>

Options:
    --frame,       -f            Wrap output in a decorative frame
    --frame-style, -s <style>    Frame style: single, double, bold, rounded (default: single)
    --padding,     -p <n>        Horizontal padding inside frame (default: 1)
    --help,        -h            Show this help message

Output: JSON object with fields: text, art, framed, frameStyle, width, height
"""

import json
import sys

# ── Font Definition ──────────────────────────────────────────────────
# Each glyph is 5 rows tall. Width varies per character.
FONT = {
    "A": [
        " AAA ",
        "A   A",
        "AAAAA",
        "A   A",
        "A   A",
    ],
    "B": [
        "BBBB ",
        "B   B",
        "BBBB ",
        "B   B",
        "BBBB ",
    ],
    "C": [
        " CCCC",
        "C    ",
        "C    ",
        "C    ",
        " CCCC",
    ],
    "D": [
        "DDDD ",
        "D   D",
        "D   D",
        "D   D",
        "DDDD ",
    ],
    "E": [
        "EEEEE",
        "E    ",
        "EEEE ",
        "E    ",
        "EEEEE",
    ],
    "F": [
        "FFFFF",
        "F    ",
        "FFFF ",
        "F    ",
        "F    ",
    ],
    "G": [
        " GGG ",
        "G    ",
        "G  GG",
        "G   G",
        " GGG ",
    ],
    "H": [
        "H   H",
        "H   H",
        "HHHHH",
        "H   H",
        "H   H",
    ],
    "I": [
        "IIIII",
        "  I  ",
        "  I  ",
        "  I  ",
        "IIIII",
    ],
    "J": [
        "JJJJJ",
        "    J",
        "    J",
        "J   J",
        " JJJ ",
    ],
    "K": [
        "K   K",
        "K  K ",
        "KKK  ",
        "K  K ",
        "K   K",
    ],
    "L": [
        "L    ",
        "L    ",
        "L    ",
        "L    ",
        "LLLLL",
    ],
    "M": [
        "M   M",
        "MM MM",
        "M M M",
        "M   M",
        "M   M",
    ],
    "N": [
        "N   N",
        "NN  N",
        "N N N",
        "N  NN",
        "N   N",
    ],
    "O": [
        " OOO ",
        "O   O",
        "O   O",
        "O   O",
        " OOO ",
    ],
    "P": [
        "PPPP ",
        "P   P",
        "PPPP ",
        "P    ",
        "P    ",
    ],
    "Q": [
        " QQQ ",
        "Q   Q",
        "Q Q Q",
        "Q  Q ",
        " QQ Q",
    ],
    "R": [
        "RRRR ",
        "R   R",
        "RRRR ",
        "R  R ",
        "R   R",
    ],
    "S": [
        " SSS ",
        "S    ",
        " SSS ",
        "    S",
        " SSS ",
    ],
    "T": [
        "TTTTT",
        "  T  ",
        "  T  ",
        "  T  ",
        "  T  ",
    ],
    "U": [
        "U   U",
        "U   U",
        "U   U",
        "U   U",
        " UUU ",
    ],
    "V": [
        "V   V",
        "V   V",
        "V   V",
        " V V ",
        "  V  ",
    ],
    "W": [
        "W   W",
        "W   W",
        "W W W",
        "WW WW",
        "W   W",
    ],
    "X": [
        "X   X",
        " X X ",
        "  X  ",
        " X X ",
        "X   X",
    ],
    "Y": [
        "Y   Y",
        " Y Y ",
        "  Y  ",
        "  Y  ",
        "  Y  ",
    ],
    "Z": [
        "ZZZZZ",
        "   Z ",
        "  Z  ",
        " Z   ",
        "ZZZZZ",
    ],
    "0": [
        " 000 ",
        "0   0",
        "0   0",
        "0   0",
        " 000 ",
    ],
    "1": [
        " 1  ",
        "11  ",
        " 1  ",
        " 1  ",
        "1111",
    ],
    "2": [
        " 222 ",
        "2   2",
        "  22 ",
        " 2   ",
        "22222",
    ],
    "3": [
        " 333 ",
        "    3",
        "  33 ",
        "    3",
        " 333 ",
    ],
    "4": [
        "4   4",
        "4   4",
        "44444",
        "    4",
        "    4",
    ],
    "5": [
        "55555",
        "5    ",
        "5555 ",
        "    5",
        "5555 ",
    ],
    "6": [
        " 666 ",
        "6    ",
        "6666 ",
        "6   6",
        " 666 ",
    ],
    "7": [
        "77777",
        "   7 ",
        "  7  ",
        " 7   ",
        "7    ",
    ],
    "8": [
        " 888 ",
        "8   8",
        " 888 ",
        "8   8",
        " 888 ",
    ],
    "9": [
        " 999 ",
        "9   9",
        " 9999",
        "    9",
        " 999 ",
    ],
    " ": [
        "   ",
        "   ",
        "   ",
        "   ",
        "   ",
    ],
    "!": [
        " ! ",
        " ! ",
        " ! ",
        "   ",
        " ! ",
    ],
    "?": [
        " ??? ",
        "    ?",
        "  ?? ",
        "     ",
        "  ?  ",
    ],
    ".": [
        "  ",
        "  ",
        "  ",
        "  ",
        " .",
    ],
    ",": [
        "  ",
        "  ",
        "  ",
        " ,",
        ", ",
    ],
    "-": [
        "     ",
        "     ",
        "-----",
        "     ",
        "     ",
    ],
    "_": [
        "     ",
        "     ",
        "     ",
        "     ",
        "_____",
    ],
    ":": [
        "  ",
        " :",
        "  ",
        " :",
        "  ",
    ],
    ";": [
        "  ",
        " ;",
        "  ",
        " ;",
        "; ",
    ],
    "'": [
        " '",
        " '",
        "  ",
        "  ",
        "  ",
    ],
    '"': [
        '" "',
        '" "',
        "   ",
        "   ",
        "   ",
    ],
    "(": [
        " (",
        "( ",
        "( ",
        "( ",
        " (",
    ],
    ")": [
        ") ",
        " )",
        " )",
        " )",
        ") ",
    ],
    "/": [
        "    /",
        "   / ",
        "  /  ",
        " /   ",
        "/    ",
    ],
    "#": [
        " # # ",
        "#####",
        " # # ",
        "#####",
        " # # ",
    ],
    "@": [
        " @@@ ",
        "@ @ @",
        "@ @@ ",
        "@    ",
        " @@@ ",
    ],
    "+": [
        "     ",
        "  +  ",
        "+++++",
        "  +  ",
        "     ",
    ],
    "=": [
        "     ",
        "=====",
        "     ",
        "=====",
        "     ",
    ],
    "*": [
        "     ",
        "* * *",
        " *** ",
        "* * *",
        "     ",
    ],
    "&": [
        " &&  ",
        "& &  ",
        " & & ",
        "& & &",
        " &&& ",
    ],
    "%": [
        "%   %",
        "   % ",
        "  %  ",
        " %   ",
        "%   %",
    ],
    "$": [
        " $$$",
        "$ $ ",
        " $$ ",
        " $ $",
        "$$$ ",
    ],
    "<": [
        "   <",
        "  < ",
        " <  ",
        "  < ",
        "   <",
    ],
    ">": [
        ">   ",
        " >  ",
        "  > ",
        " >  ",
        ">   ",
    ],
    "[": [
        " [[",
        " [ ",
        " [ ",
        " [ ",
        " [[",
    ],
    "]": [
        "]] ",
        " ] ",
        " ] ",
        " ] ",
        "]] ",
    ],
    "{": [
        "  {",
        " { ",
        "{{ ",
        " { ",
        "  {",
    ],
    "}": [
        "}  ",
        " } ",
        " }}",
        " } ",
        "}  ",
    ],
    "^": [
        " ^ ",
        "^ ^",
        "   ",
        "   ",
        "   ",
    ],
    "~": [
        "     ",
        " ~ ~ ",
        "~ ~ ~",
        "     ",
        "     ",
    ],
}

ROWS = 5
GLYPH_GAP = 1  # columns between glyphs

# ── Frame Styles ─────────────────────────────────────────────────────
FRAMES = {
    "single":  {"tl": "\u250c", "tr": "\u2510", "bl": "\u2514", "br": "\u2518", "h": "\u2500", "v": "\u2502"},
    "double":  {"tl": "\u2554", "tr": "\u2557", "bl": "\u255a", "br": "\u255d", "h": "\u2550", "v": "\u2551"},
    "bold":    {"tl": "\u250f", "tr": "\u2513", "bl": "\u2517", "br": "\u251b", "h": "\u2501", "v": "\u2503"},
    "rounded": {"tl": "\u256d", "tr": "\u256e", "bl": "\u2570", "br": "\u256f", "h": "\u2500", "v": "\u2502"},
}


def usage():
    print(__doc__.strip(), file=sys.stderr)
    sys.exit(1)


def render_text(text):
    upper = text.upper()
    lines = [""] * ROWS

    for i, ch in enumerate(upper):
        if i > 0:
            gap = " " * GLYPH_GAP
            for r in range(ROWS):
                lines[r] += gap

        glyph = FONT.get(ch)
        if glyph:
            for r in range(ROWS):
                lines[r] += glyph[r]
        else:
            # Unknown char → render as blank column
            for r in range(ROWS):
                lines[r] += " "

    # Normalize widths (right-pad shorter rows)
    max_len = max(len(line) for line in lines)
    return [line.ljust(max_len) for line in lines]


def add_frame(lines, style, padding):
    f = FRAMES.get(style)
    if not f:
        valid = ", ".join(FRAMES.keys())
        print(f'Error: unknown frame style "{style}". Choose from: {valid}', file=sys.stderr)
        sys.exit(1)

    pad = " " * padding
    inner_width = max(len(line) for line in lines) + padding * 2

    top = f["tl"] + f["h"] * inner_width + f["tr"]
    bottom = f["bl"] + f["h"] * inner_width + f["br"]

    framed = [top]
    for line in lines:
        framed.append(f["v"] + pad + line.ljust(inner_width - padding * 2) + pad + f["v"])
    framed.append(bottom)

    return framed


def parse_args(argv):
    frame = False
    frame_style = "single"
    padding = 1
    positional = []
    i = 0

    while i < len(argv):
        arg = argv[i]
        if arg in ("--help", "-h"):
            usage()
        elif arg in ("--frame", "-f"):
            frame = True
        elif arg in ("--frame-style", "-s"):
            i += 1
            if i >= len(argv):
                print("Error: --frame-style requires a value", file=sys.stderr)
                sys.exit(1)
            frame_style = argv[i]
            frame = True  # implicitly enable frame
        elif arg in ("--padding", "-p"):
            i += 1
            if i >= len(argv):
                print("Error: --padding requires a value", file=sys.stderr)
                sys.exit(1)
            try:
                padding = int(argv[i])
                if padding < 0:
                    raise ValueError
            except ValueError:
                print("Error: --padding must be a non-negative integer", file=sys.stderr)
                sys.exit(1)
        elif arg.startswith("-"):
            print(f'Error: unknown option "{arg}"', file=sys.stderr)
            sys.exit(1)
        else:
            positional.append(arg)
        i += 1

    if not positional:
        print("Error: no text provided", file=sys.stderr)
        usage()

    return {
        "frame": frame,
        "frame_style": frame_style,
        "padding": padding,
        "text": " ".join(positional),
    }


def main():
    if len(sys.argv) < 2:
        usage()

    opts = parse_args(sys.argv[1:])
    art_lines = render_text(opts["text"])

    if opts["frame"]:
        art_lines = add_frame(art_lines, opts["frame_style"], opts["padding"])

    width = max(len(line) for line in art_lines)
    height = len(art_lines)

    result = {
        "text": opts["text"],
        "art": art_lines,
        "framed": opts["frame"],
        "frameStyle": opts["frame_style"] if opts["frame"] else None,
        "width": width,
        "height": height,
    }

    print(json.dumps(result, indent=2))


if __name__ == "__main__":
    main()
