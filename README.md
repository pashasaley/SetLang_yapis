	Компилятор языка, описывающего математические вычисления.

	Как запускать:
	1) в командной строке переходим в корневой каталог проекта;
	2) запускаем скрипт Start.bat, через пробел передаём тест (Test.gc) на вход скрипта;
	3) в скрипте Start.bat вызывается скрипт Compile.bat, выполняющий непосредственно условие лабораторной работы;
	4) затем запускается скрипт Run.bat который компилирует с помощью javac Main.java файл, сгенерированный в ходе выполнения пункта 3;
	5) выводится в консоль результат работы программы.
		
		Алгоритм выполнения:
        1) в классе Compiler в методе main задан путь к исходнику текста тестовой программы.
        2) текст нашей тестовой программы передается в качестве параметра в конструктор лексического анализатора(class LexicalAnalyzer).
        3) вызывается метод токенизации(tokenize() лексического анализатора(LexicalAnalyzer)), и результат присваивается списку Token'ов(class Token).
        4) список токенов передается в парсер(class Parser), затем вызывается метод парсера parse(), результат которого сохраняется в объект statement класса Statement.
        5) вызывается метод выполнения() объекта statement(statement.execute()).

        Принип работы лексического анализатора(LexicalAnalyzer):
        В классе LexicalAnalyzer имеется Map, хранящий отображения вида: ("-", TokenType.MINUS)...
        Запуск лексического анализатора осуществляется с помощью метода tokenize().

        public List<Token> tokenize() {
                while (currentPosition < length) {
                    final char currentChar = peek(0);
                    if (Character.isDigit(currentChar)) {
                        tokenizeNumber();
                    } else if (Character.isLetter(currentChar)) {
                        tokenizeWord();
                    } else if (OPERATOR_CHARS.indexOf(currentChar) != -1) {
                        tokenizeOperator();
                    } else if (currentChar == '"') {
                        TokenizeText();
                    } else {
                        next(); // Skip other chars(whitespaces...)
                    }
                }
                return tokens;
            }

        В данном методе в цикле просматриваем все символы исходника, и если встречаем:
		- цифру(isDigit) - вызываем метод tokenizeNumber();
		- букву(isLetter)- вызываем метод tokenizeWord(); 
		- оператор(OPERATOR_CHARS) - вызываем метод tokenizeOperator();  
        - кавычки(")- вызываем метод TokenizeText(). 
		Каждый из этих методов обрабатывает все последующие символы(пока не наткнемся на символ, отличный от предыдущего, т.е. дошли до конца текущего токена)
        посредством создания буффера(StringBuilder buffer) и сохранения символов текущего токена в него(буфер). Затем на основе этого буфера,
        в зависимости от текущего метода, определяем, что это за токен и заносим его в список токенов. Каждый токен имеет свой тип и содержимое:

        Token(TokenType type, String text) {
                this.type = type;
                this.text = text;
            }

        Все токены добавляются в список и хранятся там в том порядке, в котором они встречаются в тексте нашего исходника. После того как весь текст
        пройден, tokenize() возвращает список наших токенов. Далее этот список попадает в парсер(Parser), который, с свою очередь, запускает парсинг
        посредством вызова метода parse().

        public Statement parse() {
                final BlockStatement blockStatement = new BlockStatement();
                while (!match(TokenType.EOF)) {
                    blockStatement.add(statement());
                }
                return blockStatement;
            }

        В данном методе мы создаем объект blockStatement класса BlockStatement, который содержит список входящих в него операторов:

        public class BlockStatement implements Statement {
            private List<Statement> statements;
            ...

        Циклом проходимся по списку наших токенов, полученных ранее с помощью лексического анализатора, и добавляем в blockStatement результат вызова statement().

        private Statement statement() {
                if (match(TokenType.PRINT)) {
                    return new PrintStatement(expression());
                }
                if (match(TokenType.IF)) {
                    return ifElse();
                }
                if (match(TokenType.DO)) {
                    return doWhileStatement();
                }
                if (match(TokenType.WHILE)) {
                    return whileStatement();
                }
                if (match(TokenType.FOR)) {
                    return forStatement();
                }
                if (match(TokenType.BREAK)) {
                    return new BreakStatement();
                }
                if (match(TokenType.CONTINUE)) {
                    return new ContinueStatement();
                }
                if (match(TokenType.DEF)) {
                    return functionDefine();
                }
                if (peek(0).getType() == TokenType.WORD && peek(1).getType() == TokenType.LPAREN) {
                    return new FunctionStatement(function());
                }
                return assignmentStatement();
            }
    
    В данном методе мы проверяем текущий токен на принадлежность одному из типов токенов(TokenType) и вызываем соответствующий метод 
    из стека методов, построенном на приоритете операций.
    
    private Statement block() {
        final BlockStatement blockStatement = new BlockStatement();
        consume(TokenType.LBRACE); {
        while (!match(TokenType.RBRACE)) {
            blockStatement.add(statement());
        }
        return blockStatement;
    }

    private Statement statementOrBlock() {
        if (peek(0).getType() == TokenType.LBRACE) {
            return block();
        }
        return statement();
    }

    private Statement statement() {
        if (match(TokenType.PRINT)) {
            return new PrintStatement(expression());
        }
        if (match(TokenType.IF)) {
            return ifElse();
        }
        if (match(TokenType.DO)) {
            return doWhileStatement();
        }
        ...
    
    В зависимости от текущего токена мы спускаемся по стеку вниз пока не дойдем до обрабатывающего этот токен метода, возвращая каждый раз 
    результат выполнения текущего метода в предыдущий. Таким образом все выражения добавляются в нужном порядке(так же соблюдается
    вложенность выражений) и выполняются(считаются) в том же порядке, соблюдая все правила приоритета операций. По данному принципу
    обрабатываются все токены.
    После завершения парсинга наше выражение statement начинает свое "выполнение" по вызову метода execute():
    
    statement.execute();
    
    Начинается выполнение всех входящих в statement выражений, соблюдая вложенность.
    При отсутствии ошибок получаем вывод в консоль обработанного текста нашего исходника.
    
