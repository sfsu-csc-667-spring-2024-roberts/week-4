#!/opt/homebrew/bin/node

const body = "<html><body>Hello world [" + Math.random() + "]</body><html>";

process.stdout.write(`Content-Length: ${body.length}\r\n`);
process.stdout.write("Content-Type: text/html\r\n");
process.stdout.write("\r\n");
process.stdout.write(body);
