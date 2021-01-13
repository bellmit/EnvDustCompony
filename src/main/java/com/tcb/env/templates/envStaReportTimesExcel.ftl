<?xml version="1.0"?>
<?mso-application progid="Excel.Sheet"?>
<Workbook xmlns="urn:schemas-microsoft-com:office:spreadsheet"
 xmlns:o="urn:schemas-microsoft-com:office:office"
 xmlns:x="urn:schemas-microsoft-com:office:excel"
 xmlns:ss="urn:schemas-microsoft-com:office:spreadsheet"
 xmlns:html="http://www.w3.org/TR/REC-html40">
 <DocumentProperties xmlns="urn:schemas-microsoft-com:office:office">
  <Created>2006-09-16T00:00:00Z</Created>
  <LastSaved>2018-05-07T01:38:57Z</LastSaved>
  <Version>15.00</Version>
 </DocumentProperties>
 <OfficeDocumentSettings xmlns="urn:schemas-microsoft-com:office:office">
  <AllowPNG/>
  <RemovePersonalInformation/>
 </OfficeDocumentSettings>
 <ExcelWorkbook xmlns="urn:schemas-microsoft-com:office:excel">
  <WindowHeight>8010</WindowHeight>
  <WindowWidth>14810</WindowWidth>
  <WindowTopX>240</WindowTopX>
  <WindowTopY>110</WindowTopY>
  <ProtectStructure>False</ProtectStructure>
  <ProtectWindows>False</ProtectWindows>
 </ExcelWorkbook>
 <Styles>
  <Style ss:ID="Default" ss:Name="Normal">
   <Alignment ss:Vertical="Bottom"/>
   <Borders/>
   <Font ss:FontName="宋体" x:CharSet="134" ss:Size="11" ss:Color="#000000"/>
   <Interior/>
   <NumberFormat/>
   <Protection/>
  </Style>
  <Style ss:ID="s72">
   <Alignment ss:Horizontal="Center" ss:Vertical="Bottom"/>
   <Borders>
    <Border ss:Position="Bottom" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Left" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Right" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Top" ss:LineStyle="Continuous" ss:Weight="1"/>
   </Borders>
   <Interior ss:Color="#BFBFBF" ss:Pattern="Solid"/>
  </Style>
  <Style ss:ID="s77">
   <Alignment ss:Horizontal="Center" ss:Vertical="Bottom" ss:ShrinkToFit="1"/>
   <Borders>
    <Border ss:Position="Bottom" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Left" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Right" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Top" ss:LineStyle="Continuous" ss:Weight="1"/>
   </Borders>
   <NumberFormat ss:Format="@"/>
  </Style>
  <Style ss:ID="s78">
   <Alignment ss:Horizontal="Center" ss:Vertical="Center"/>
   <Font ss:FontName="宋体" x:CharSet="134" ss:Size="11" ss:Color="#000000"
    ss:Bold="1"/>
   <NumberFormat ss:Format="@"/>
  </Style>
  <Style ss:ID="s79">
   <NumberFormat ss:Format="@"/>
  </Style>
 </Styles>
 <Worksheet ss:Name="统计报表">
  <Table ss:ExpandedColumnCount="9" x:FullColumns="1"
   x:FullRows="1" ss:DefaultRowHeight="14">
   <Column ss:Index="2" ss:Width="169.5"/>
   <Column ss:Width="157.5"/>
   <Column ss:Width="51"/>
   <Column ss:Width="65"/>
   <Column ss:Width="65"/>
   <Column ss:AutoFitWidth="0" ss:Width="54.5"/>
   <Row>
    <Cell ss:Index="2" ss:MergeAcross="7" ss:MergeDown="1" ss:StyleID="s78"><Data
      ss:Type="String">${envTitle!}</Data></Cell>
   </Row>
   <Row ss:Index="3">
    <Cell ss:Index="2" ss:StyleID="s79"/>
    <Cell ss:StyleID="s79"/>
    <Cell ss:StyleID="s79"/>
    <Cell ss:StyleID="s79"><Data ss:Type="String">导出时间：</Data></Cell>
    <Cell ss:StyleID="s79"><Data ss:Type="String">${outTime!}</Data></Cell>
    <Cell ss:StyleID="s79"/>
    <Cell ss:StyleID="s79"/>
   </Row>
   <Row>
    <Cell ss:Index="2" ss:StyleID="s72"><Data ss:Type="String">所属区域</Data></Cell>
    <Cell ss:StyleID="s72"><Data ss:Type="String">设备名称</Data></Cell>
    <Cell ss:StyleID="s72"><Data ss:Type="String">监测物</Data></Cell>
    <Cell ss:StyleID="s72"><Data ss:Type="String">开始时间</Data></Cell>
	<Cell ss:StyleID="s72"><Data ss:Type="String">结束时间</Data></Cell>
    <Cell ss:StyleID="s72"><Data ss:Type="String">最大值</Data></Cell>
    <Cell ss:StyleID="s72"><Data ss:Type="String">最小值</Data></Cell>
    <Cell ss:StyleID="s72"><Data ss:Type="String">平均值</Data></Cell>
   </Row>
	<#if (envStaList?size>0) >
		<#list envStaList as itemValue>
		   <Row>
			<Cell ss:Index="2" ss:StyleID="s77"><Data ss:Type="String">${itemValue.areaName!}</Data></Cell>
			<Cell ss:StyleID="s77"><Data ss:Type="String">${itemValue.deviceName!}</Data></Cell>
			<Cell ss:StyleID="s77"><Data ss:Type="String">${itemValue.thingName!}</Data></Cell>
			<Cell ss:StyleID="s77"><Data ss:Type="String">${itemValue.beginTime!}</Data></Cell>
			<Cell ss:StyleID="s77"><Data ss:Type="String">${itemValue.endTime!}</Data></Cell>
			<Cell ss:StyleID="s77"><Data ss:Type="String">${itemValue.thingMax!}</Data></Cell>
			<Cell ss:StyleID="s77"><Data ss:Type="String">${itemValue.thingMin!}</Data></Cell>
			<Cell ss:StyleID="s77"><Data ss:Type="String">${itemValue.thingAvg!}</Data></Cell>
		   </Row>
   	   </#list>
	</#if>
   <Row ss:AutoFitHeight="0">
    <Cell ss:Index="2" ss:StyleID="s77"/>
    <Cell ss:StyleID="s77"/>
    <Cell ss:StyleID="s77"/>
    <Cell ss:StyleID="s77"/>
    <Cell ss:StyleID="s77"/>
	<Cell ss:StyleID="s77"/>
    <Cell ss:StyleID="s77"/>
    <Cell ss:StyleID="s77"/>
   </Row>
  </Table>
  <WorksheetOptions xmlns="urn:schemas-microsoft-com:office:excel">
   <PageSetup>
    <Header x:Margin="0.3"/>
    <Footer x:Margin="0.3"/>
    <PageMargins x:Bottom="0.75" x:Left="0.7" x:Right="0.7" x:Top="0.75"/>
   </PageSetup>
   <Print>
    <ValidPrinterInfo/>
    <PaperSizeIndex>9</PaperSizeIndex>
    <HorizontalResolution>-3</HorizontalResolution>
    <VerticalResolution>0</VerticalResolution>
   </Print>
   <Selected/>
   <FreezePanes/>
   <FrozenNoSplit/>
   <SplitHorizontal>4</SplitHorizontal>
   <TopRowBottomPane>4</TopRowBottomPane>
   <ActivePane>2</ActivePane>
   <Panes>
    <Pane>
     <Number>3</Number>
    </Pane>
    <Pane>
     <Number>2</Number>
     <ActiveRow>12</ActiveRow>
     <ActiveCol>5</ActiveCol>
    </Pane>
   </Panes>
   <ProtectObjects>False</ProtectObjects>
   <ProtectScenarios>False</ProtectScenarios>
  </WorksheetOptions>
 </Worksheet>
</Workbook>
