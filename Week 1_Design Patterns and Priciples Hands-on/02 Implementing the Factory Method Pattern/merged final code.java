public class Main {

    // Document Interface
    interface Document {
        void open();
    }

    // WordDocument class
    static class WordDocument implements Document {
        public void open() {
            System.out.println("Opening Word Document...");
        }
    }

    // PdfDocument class
    static class PdfDocument implements Document {
        public void open() {
            System.out.println("Opening PDF Document...");
        }
    }

    // ExcelDocument class
    static class ExcelDocument implements Document {
        public void open() {
            System.out.println("Opening Excel Document...");
        }
    }

    // Abstract Factory
    static abstract class DocumentFactory {
        public abstract Document createDocument();
    }

    // Concrete Factory: Word
    static class WordFactory extends DocumentFactory {
        public Document createDocument() {
            return new WordDocument();
        }
    }

    // Concrete Factory: PDF
    static class PdfFactory extends DocumentFactory {
        public Document createDocument() {
            return new PdfDocument();
        }
    }

    // Concrete Factory: Excel
    static class ExcelFactory extends DocumentFactory {
        public Document createDocument() {
            return new ExcelDocument();
        }
    }

    // Main method to test factory method pattern
    public static void main(String[] args) {
        DocumentFactory wordFactory = new WordFactory();
        Document wordDoc = wordFactory.createDocument();
        wordDoc.open();

        DocumentFactory pdfFactory = new PdfFactory();
        Document pdfDoc = pdfFactory.createDocument();
        pdfDoc.open();

        DocumentFactory excelFactory = new ExcelFactory();
        Document excelDoc = excelFactory.createDocument();
        excelDoc.open();
    }
}
