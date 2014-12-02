semreview
=========

Text classification taking advantage of the Semantic Web. This is named Semreview because it was initially conceived to support Systematic Literature Reviews, using Semantic enrichment to improve text classification.

How to build
============

```
mvn install
```

How to use it
=============

_To be written_

Internal architecture
=====================
The system is mainly a pipeline:

1) DataSetProvider: 
	the first stage provide the documents
	
2) PaperEnricher: 
	the second stage enrich the documents

3) Classifier:
	the third stage compare enricheded documents to the model

Authors
=======

The project was initially developed by:
* [Federico Tomassetti](https://github.com/ftomassetti)
* [Giuseppe Rizzo](https://github.com/giusepperizzo)
* Antonio Vetro'
* Luca Ardito
* Marco Torchiano.

History
=======

The project was initially available on [Sourceforge](http://sourceforge.net/projects/semreview/) and it has been moved to GitHub on late 2014.
