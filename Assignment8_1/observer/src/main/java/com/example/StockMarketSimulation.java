package com.example;

import java.util.HashMap;
import java.util.List;
import java.util.*;
import java.util.Map;
import java.util.Scanner;

public class StockMarketSimulation {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<String, Stock> stocks = new HashMap<>();
        Map<String, Investor> investors = new HashMap<>();
        boolean exit = false;

        while (!exit) {
            System.out.println("\n--- Stock Market Simulation Menu ---");
            System.out.println("1. Register investor to a stock");
            System.out.println("2. Unregister investor from a stock");
            System.out.println("3. Update stock price");
            System.out.println("4. Create new stock");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    registerInvestorToStock(scanner, stocks, investors);
                    break;
                case 2:
                    unregisterInvestorFromStock(scanner, stocks, investors);
                    break;
                case 3:
                    updateStockPrice(scanner, stocks);
                    break;
                case 4:
                    createNewStock(scanner, stocks);
                    break;
                case 5:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 5.");
            }
        }

        scanner.close();
    }

    private static void registerInvestorToStock(Scanner scanner, Map<String, Stock> stocks, Map<String, Investor> investors) {
        System.out.print("Enter investor name: ");
        String investorName = scanner.next();
        System.out.print("Enter stock symbol to register investor: ");
        String stockSymbol = scanner.next();
        if (!investors.containsKey(investorName)) {
            investors.put(investorName, new Investor(investorName));
        }
        Investor investor = investors.get(investorName);
        if (stocks.containsKey(stockSymbol)) {
            Stock stock = stocks.get(stockSymbol);
            stock.registerInvestor(investor);
        } else {
            System.out.println("Stock with symbol " + stockSymbol + " does not exist.");
        }
    }

    private static void unregisterInvestorFromStock(Scanner scanner, Map<String, Stock> stocks, Map<String, Investor> investors) {
        System.out.print("Enter investor name: ");
        String investorName = scanner.next();
        System.out.print("Enter stock symbol to unregister investor: ");
        String stockSymbol = scanner.next();
        if (investors.containsKey(investorName)) {
            Investor investor = investors.get(investorName);
            if (stocks.containsKey(stockSymbol)) {
                Stock stock = stocks.get(stockSymbol);
                stock.unregisterInvestor(investor);
            } else {
                System.out.println("Stock with symbol " + stockSymbol + " does not exist.");
            }
        } else {
            System.out.println("Investor " + investorName + " does not exist.");
        }
    }

    private static void updateStockPrice(Scanner scanner, Map<String, Stock> stocks) {
        System.out.print("Enter stock symbol to update price: ");
        String stockSymbol = scanner.next();
        System.out.print("Enter new price: ");
        double newPrice = scanner.nextDouble();
        if (stocks.containsKey(stockSymbol)) {
            Stock stock = stocks.get(stockSymbol);
            stock.updatePrice(newPrice);
        } else {
            System.out.println("Stock with symbol " + stockSymbol + " does not exist.");
        }
    }

    private static void createNewStock(Scanner scanner, Map<String, Stock> stocks) {
        System.out.print("Enter stock symbol: ");
        String symbol = scanner.next();
        System.out.print("Enter price: ");
        double price = scanner.nextDouble();
        stocks.put(symbol, new Stock(symbol, price));
        String border = "+---------------------------------+";
        System.out.println(border);
        System.out.println("| Stock with symbol " + symbol + " created. | ");
        System.out.println(border);
    }
}

class Stock {
    private String symbol;
    private double price;
    private List<Investor> investors;

    public Stock(String symbol, double price) {
        this.symbol = symbol;
        this.price = price;
        this.investors = new ArrayList<>();
    }

    public void registerInvestor(Investor investor) {
        if (investor != null) {
            String border = "+------------------------------------------------------+";
            if (!investors.contains(investor)) {
                investors.add(investor);
                investor.addStock(this);
                System.out.println(border);
                System.out.println("| "+ investor.getName() + " has been registered as an investor for " + symbol + ". |");
                System.out.println(border);
            } else {
                System.out.println(border);
                System.out.println("| "+ investor.getName() + " is already registered as an investor for " + symbol + ". |");
                System.out.println(border);
            }
        } else {
            throw new IllegalArgumentException("Investor cannot be null.");
        }
    }

    public void unregisterInvestor(Investor investor) {
        String border = "+---------------------------------------------------------+";
        if (investors.contains(investor)) {
            investors.remove(investor);
            investor.removeStock(this);
            System.out.println(border);
            System.out.println("| " + investor.getName() + " has been unregistered as an investor for " + symbol + ".  |");
            System.out.println(border);
        } else {
            System.out.println(border);
            System.out.println(investor.getName() + " is not registered as an investor for " + symbol + ".");
            System.out.println(border);
        }
    }

    public void updatePrice(double price) {
        this.price = price;
        notifyInvestors();
    }

    private void notifyInvestors() {
        for (Investor investor : investors) {
            investor.update(this);
        }
    }

    public String getSymbol() {
        return symbol;
    }

    public double getPrice() {
        return price;
    }
}

class Investor {
    private String name;
    private List<Stock> stocks;

    public Investor(String name) {
        this.name = name;
        this.stocks = new ArrayList<>();
    }

    public void update(Stock stock) {
        System.out.println();
        String border = "+--------------------------------------------+";
        System.out.println(border);
        System.out.println("|              Investor Update               |");
        System.out.println(border);
        System.out.println("| Investor: " + String.format("%-30s", name) + "   |");
        System.out.println("| Stock:    " + String.format("%-30s", stock.getSymbol()) + "   |");
        System.out.println("| New Price: $" + String.format("%-24.2f", stock.getPrice()) + "       |");
        System.out.println(border);
    }
    

    public void addStock(Stock stock) {
        stocks.add(stock);
    }

    public void removeStock(Stock stock) {
        stocks.remove(stock);
    }

    public String getName() {
        return name;
    }
}
