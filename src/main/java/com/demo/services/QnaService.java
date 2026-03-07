package com.demo.services;
import com.demo.client.ProductApiClient;
import org.springframework.stereotype.Service;

@Service
public class QnaService {
    private final GeminiService geminiService;
    private final ProductApiClient productApiClient;

    public QnaService(GeminiService geminiService, ProductApiClient productApiClient) {
        this.geminiService = geminiService;
        this.productApiClient = productApiClient;
    }

    public String answerQuestion(String question) {
        String products;
        try {
            products = productApiClient.fetchProductsJson();
        } catch (Exception e) {
            System.err.println("ProductApiClient ERROR: " + e.getMessage());
            products = "[]";
        }

        String prompt = """
            Product data:
            %s
            Question:
            %s
            Answer ONLY in md format. with little sentences and mentioning types where required.
            But any question related to fertilzers and what to do with any produce are fine.
            Answer the question realated to originPlace . Based on its quality, name, focus on description for extra information. Answer anything that we get as api response.
            For question that make you list things, use numbering like 1,2...
            Make sure to give proper spaces/line breaks in the answer.
            NOTE IMPORTANT: Any question that you feel is not related to products should be answered by : Not Relevent.
            """.formatted(products, question);

        return geminiService.askGemini(prompt);
    }
}