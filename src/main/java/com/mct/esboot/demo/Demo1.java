package com.mct.esboot.demo;

import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.store.ByteBuffersDirectory;
import org.apache.lucene.store.Directory;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @program: esboot
 * @description:
 * @author: machitao
 * @create: 2023-02-18 00:19
 **/
@Service
@Slf4j
public class Demo1 {
    private Directory directory;

    private String[] ids = {"1", "2"};
    private String[] unindexed = {"netherlands", "italy"};
    private String[] unstored = {"Amsterdam has lots of briges", "venice hash lots of canals"};
    private String[] text = {"Amsterdam", "venice"};

    public Demo1() {
        this.directory = new ByteBuffersDirectory();
    }

    public void testAddData() throws IOException {
        IndexWriter writer = getWriter();
        for (int i = 0; i < ids.length; i++) {
            Document doc = new Document();
            doc.add(new StringField("id", ids[i], Field.Store.YES));
            doc.add(new StoredField("country", unindexed[i]));
            doc.add(new TextField("contents", unstored[i], Field.Store.YES));
            doc.add(new TextField("city", text[i], Field.Store.YES));
            writer.addDocument(doc);
        }
        writer.close();
    }

    public Document testSearch(String fieldName, String val) throws IOException {
        IndexReader reader = DirectoryReader.open(directory);
        IndexSearcher searcher = new IndexSearcher(reader);

        Term t = new Term(fieldName, val);
        Query query = new TermQuery(t);
        int doc = searcher.search(query, 1).scoreDocs[0].doc;
        Document doc1 = searcher.doc(doc);
        return doc1;
    }

    public IndexWriter getWriter() throws IOException {
        IndexWriterConfig config = new IndexWriterConfig(new WhitespaceAnalyzer());
        return new IndexWriter(directory, config);
    }
    public void testDeleteBeforeOptimize() throws IOException {
        IndexWriter writer = getWriter();
        System.out.println("索引中管理文档的数量：" + writer.numRamDocs() + ", 程序写入文档的数量：" + ids.length);

        writer.deleteDocuments(new Term("id", "1"));
        writer.commit();
        System.out.println("--------------------------");
        System.out.println("索引中是否包含删除标记：" + writer.hasDeletions());
        System.out.println("索引中管理文档的数量<writer.maxDoc>：" + writer.numRamDocs());
    }

}
